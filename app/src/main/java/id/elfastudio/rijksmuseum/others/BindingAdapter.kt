package id.elfastudio.rijksmuseum.others

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

class BindingAdapter {

    companion object {
        @JvmStatic
        @BindingAdapter("app:errorFromResource")
        fun setErrorFromResource(textInputLayout: TextInputLayout, id: Int) {
            if (id > 0)
                textInputLayout.error = textInputLayout.context.getString(id)
        }
    }

}