package com.example.pyxiskapri.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.pyxiskapri.R
import kotlinx.android.synthetic.main.activity_new_post.*
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.LayoutInflaterCompat.setFactory
import androidx.core.view.size

class NewPostActivity : AppCompatActivity() {

    private var images: ArrayList<Uri?>? = null

    private var position=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_post)

        //text.text="Nesto"


        images = ArrayList()

        imageSwitcher.setFactory { ImageView(applicationContext) }

     //   previousBtn.isEnabled=false
      //  nextBtn.isEnabled=false

        pickImagesBtn.setOnClickListener(){
            pickImagesIntent()
        }

        previousBtn.setOnClickListener(){
            if(position>0)
            {
                position--
                imageSwitcher.setImageURI(images!![position])
              /*  if(position==0)
                    previousBtn.isEnabled=false
                if(position<images!!.size-1)
                    nextBtn.isEnabled=true
*/
            }
            else
            {
                Toast.makeText(this,"No previous photos!",Toast.LENGTH_SHORT).show()
            }
        }

        nextBtn.setOnClickListener(){
            if(position<images!!.size-1)
            {
                position++
                imageSwitcher.setImageURI(images!![position])
               // previousBtn.isEnabled=true
               /* if(position>0)
                    previousBtn.isEnabled=true
                if(position==images!!.size-1)
                    nextBtn.isEnabled=false*/

            }
            else
            {
                Toast.makeText(this,"No following photos!",Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun pickImagesIntent(){
        val intent:Intent=Intent()
        intent.type="image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        intent.action=Intent.ACTION_GET_CONTENT
        resultLauncher.launch(intent)
    }

    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data

            if (data!!.clipData != null) {
                images!!.clear()
                val count = data.clipData!!.itemCount
                for (i in 0 until count) {
                    val imageUri = data.clipData!!.getItemAt(i).uri
                    images!!.add(imageUri)
                }

                imageSwitcher.setImageURI(images!![0])
                position = 0
            } else {
                val imageUri = data.data
                imageSwitcher.setImageURI(imageUri)
                position = 0
            }


        }

    }
}