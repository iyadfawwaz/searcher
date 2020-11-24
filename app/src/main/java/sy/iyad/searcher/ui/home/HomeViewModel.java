package sy.iyad.searcher.ui.home;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("مرحبا بكم في برنامج \nالبحث عن كلمات السر");
    }

    public LiveData<String> getText() {
        return mText;
    }
}