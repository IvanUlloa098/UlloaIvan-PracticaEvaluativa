package ec.edu.ups.ulloaivan_practicaevaluativa

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.navigation.fragment.findNavController
import ec.edu.ups.ulloaivan_practicaevaluativa.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var firstBinding: FragmentFirstBinding? = null
    private var list = ArrayList<String>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val _binding = FragmentFirstBinding.bind(view)
        firstBinding = _binding

        var textUser : EditText = view.findViewById(R.id.editTextTextPersonName)
        var textPass : EditText = view.findViewById(R.id.editTextTextPassword)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {

            val user : String = textUser.text.toString()
            val pass : String = textPass.text.toString()

            try {
                list = loadData()

                for (item in list) {
                    var aux = item.split(",").toString()

                    if(aux.elementAt(0).toString() == user && aux.elementAt(1).toString() == pass) {
                        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                        break
                    }

                }

            } catch (e: Exception) {
                // handler
            }

        }

        view.findViewById<Button>(R.id.button_register).setOnClickListener {

            val user : String = textUser.text.toString()
            val pass : String = textPass.text.toString()

            if(user != "" && pass != "") {
                try {
                    list = loadData()
                } catch (e: Exception) {
                    // handler
                }

                list.add("$user,$pass")
                saveData(list)

                textUser.setText("")
                textPass.setText("")
            }

        }
    }

    fun saveData(list: ArrayList<String>){
        val gson = Gson()
        val json = gson.toJson(list)//converting list to Json
        println(json.toString())
        val editor = getContext()?.getSharedPreferences("LIST", Context.MODE_PRIVATE)?.edit()
        editor?.putString("LIST", json)
        editor?.apply()
    }

    fun loadData():ArrayList<String>{
        val gson = Gson()
        val json = getContext()?.getSharedPreferences("LIST", Context.MODE_PRIVATE)?.getString("LIST", null)
        val type = object : TypeToken<ArrayList<String>>(){}.type//converting the json to list
        return gson?.fromJson(json, type)//returning the list
    }
}