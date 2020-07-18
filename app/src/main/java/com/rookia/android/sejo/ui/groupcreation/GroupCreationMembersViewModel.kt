package com.rookia.android.sejo.ui.groupcreation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.rookia.android.androidutils.data.preferences.PreferencesManager
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.normalizedString
import com.rookia.android.sejo.Constants
import com.rookia.android.sejo.di.modules.ProvidesModule
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.usecases.CreateGroupUseCase
import com.rookia.android.sejo.usecases.GetContactsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

@ExperimentalCoroutinesApi
@FlowPreview
class GroupCreationMembersViewModel @ViewModelInject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    private val preferencesManager: PreferencesManager,
    @ProvidesModule.IODispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val QUERY_DEBOUNCE = 500L
    }

    private lateinit var _phoneContactsList: LiveData<Result<List<PhoneContact>>>
    val phoneContactsList: MediatorLiveData<Result<List<PhoneContact>>> = MediatorLiveData()

    private lateinit var _groupCreationResponse: LiveData<Result<Unit>>
    val groupCreationResponse: MediatorLiveData<Result<Unit>> = MediatorLiveData()

    val queryChannel = ConflatedBroadcastChannel<String>()


    init {

            queryChannel.asFlow()
                .debounce(QUERY_DEBOUNCE)
                .onEach {
                    if (::_phoneContactsList.isInitialized) {
                        phoneContactsList.value =
                            Result.success(getListFiltered(_phoneContactsList.value?.data, it))
                    }
                }
                .launchIn(viewModelScope)

    }

    fun loadPhoneContacts() {
        try {
            _phoneContactsList = getContactsUseCase.loadContacts().asLiveData(dispatcher)
            phoneContactsList.addSource(_phoneContactsList) {
                phoneContactsList.value =
                    Result.from(it.status, getListFiltered(it.data, ""))
                if (it.status != Result.Status.LOADING) {
                    phoneContactsList.removeSource(_phoneContactsList)
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

    private fun getListFiltered(list: List<PhoneContact>?, query: String): List<PhoneContact> {
        return list
            ?.sortedBy { contact -> contact.name.normalizedString() }
            ?.filter { it.name.normalizedString().toLowerCase(Locale.getDefault()).contains(query) }
            ?: listOf()
    }

    fun createGroup(name: String, fee: Int, members: List<PhoneContact>) {
        preferencesManager.getStringFromPreferences(Constants.UserData.ID_TAG)?.let { userId ->
            _groupCreationResponse =
                createGroupUseCase.createGroup(name, fee, userId, members).asLiveData(dispatcher)
            groupCreationResponse.addSource(_groupCreationResponse) {
                groupCreationResponse.value = it
            }
        }
    }

}
