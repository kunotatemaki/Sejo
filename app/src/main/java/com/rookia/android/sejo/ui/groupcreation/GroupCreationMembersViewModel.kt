package com.rookia.android.sejo.ui.groupcreation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.domain.local.PhoneContact
import com.rookia.android.sejo.usecases.GetContactsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named

class GroupCreationMembersViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    @Named("IO") private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private lateinit var _phoneContactsList: LiveData<Result<List<PhoneContact>>>
    val phoneContactsList: MediatorLiveData<Result<List<PhoneContact>>> = MediatorLiveData()

    fun loadPhoneContacts() {
        try {
            _phoneContactsList = getContactsUseCase.loadContacts().asLiveData(dispatcher)
            phoneContactsList.addSource(_phoneContactsList) {
                phoneContactsList.value =
                    Result.from(it.status, it.data?.sortedBy { contact -> contact.name })
                if (it.status != Result.Status.LOADING) {
                    phoneContactsList.removeSource(_phoneContactsList)
                }
            }
        } catch (e: NumberFormatException) {
        }
    }

}
