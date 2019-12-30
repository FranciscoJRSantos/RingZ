package com.example.ringz.views


import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ringz.R
import com.example.ringz.models.Home
import com.example.ringz.models.User
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {
    private lateinit var mainActivity: MainActivity
    private var home: Home? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var userRef : DatabaseReference
    private var newUser: User?=null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)


        mainActivity = this.activity as MainActivity
        home = mainActivity.home

        return view
    }

    override fun onStart() {
        super.onStart()

        house_name.text = home?.name
        house_name_field.text = Editable.Factory.getInstance().newEditable(home?.name);
        house_code.text = home?.uuid

        edit_name_button.setOnClickListener(this)
        save_name_button.setOnClickListener(this)
        remove_house.setOnClickListener(this)
        add_member.setOnClickListener(this)
        copy_code_button.setOnClickListener(this)
        toggle_state.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onToggleChange(isChecked)
            } else {
                onToggleChange(isChecked)
            }
        }


    }

    private fun onHouseEditClick(view : View) {

        home?.name = house_name_field.text.toString()
        home?.save()

        house_name.text = home?.name
        house_name_field.text = Editable.Factory.getInstance().newEditable(home?.name);

        home_switcher.showPrevious()
        button_switcher.showPrevious()
        dismissKeyboard()
    }

    private fun onHouseNameClick(view : View) {
        home_switcher.showNext()
        button_switcher.showNext()
        showKeyboard(house_name_field)
    }

    private fun onHouseDeleteClick(view: View) {
        mainActivity.user!!.removeHouse()
        home?.delete()
        mainActivity.renderFragment(ProfileFragment())
    }

    private fun onToggleChange(isChecked : Boolean) {
        home?.toggleState(isChecked)
        if(isChecked) {
            Toast.makeText(mainActivity.baseContext, "Casa Aberta.", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(mainActivity.baseContext, "Casa Fechada.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.edit_name_button -> onHouseNameClick(v)
            R.id.save_name_button -> onHouseEditClick(v)
            R.id.remove_house -> onHouseDeleteClick(v)
            R.id.copy_code_button -> copyToClipboard(v)
            R.id.add_member -> addMember(v)
            R.id.save_newMember_button -> inviteMember(v)
        }
    }

    private fun inviteMember(v: View) {
        val id = new_member_mail_field.text.toString()
        userRef  = database.getReference("users").child(id)

        val userListener = object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                newUser = null
            }

            override fun onDataChange(databaseSnapshot: DataSnapshot) {
                newUser = databaseSnapshot.getValue(User::class.java)!!
                if(newUser!!.houseId!=null)
                    newUser!!.attachHouse(home!!.uuid)
            }
        }

        userRef.addListenerForSingleValueEvent(userListener)
    }

    private fun addMember(view: View) {
        member_switcher.showNext()
    }

    private fun dismissKeyboard() {
        val view: View? = mainActivity.currentFocus
        if (view != null) {
            val inputManager: InputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun showKeyboard(editTextField: EditText) {
        editTextField.requestFocus();
        editTextField.postDelayed({
            val keyboard: InputMethodManager = mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            keyboard.showSoftInput(editTextField,0);
        }, 200)
    }

    private fun copyToClipboard(view : View) {
        var clipboard = context!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip : ClipData = ClipData.newPlainText("copied code", house_code.text.toString())
        clipboard.setPrimaryClip(clip)
        Toast.makeText(mainActivity.baseContext, "Código copiado.", Toast.LENGTH_LONG).show()
    }
}
