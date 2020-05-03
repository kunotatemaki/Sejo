package com.rookia.android.sejo.ui.groupcreation

import androidx.lifecycle.*
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.androidutils.extensions.normalizedString
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.usecases.CreateGroupUseCase
import com.rookia.android.sejo.usecases.GetContactsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import java.util.*
import javax.inject.Inject
import javax.inject.Named

class GroupCreationMembersViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val createGroupUseCase: CreateGroupUseCase,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var _phoneContactsList: LiveData<Result<List<PhoneContact>>>
    val phoneContactsList: MediatorLiveData<Result<List<PhoneContact>>> = MediatorLiveData()
    private val _query: MutableLiveData<String> = MutableLiveData()

    init {
        phoneContactsList.addSource(_query) {
            if (::_phoneContactsList.isInitialized) {
                phoneContactsList.value =
                    Result.success(getListFiltered(_phoneContactsList.value?.data, it))
            }
        }
    }

    fun loadPhoneContacts() {
        try {
            _phoneContactsList = getContactsUseCase.loadContacts().asLiveData(dispatcher)
            phoneContactsList.addSource(_phoneContactsList) {
                phoneContactsList.value =
                    Result.from(it.status, getListFiltered(it.data, _query.value ?: ""))
                if (it.status != Result.Status.LOADING) {
                    phoneContactsList.removeSource(_phoneContactsList)
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

    fun query(text: String?) {
        _query.value = text
    }

    private fun getListFiltered(list: List<PhoneContact>?, query: String): List<PhoneContact> {
        return list
            ?.sortedBy { contact -> contact.name.normalizedString() }
            ?.filter { it.name.normalizedString().toLowerCase(Locale.getDefault()).contains(query) }
            ?: listOf()
    }

    fun createGroup(name: String, fee: Int, nAdmins: Int, members: List<PhoneContact>) {

    }

}
