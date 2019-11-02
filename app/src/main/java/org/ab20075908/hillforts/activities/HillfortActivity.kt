package org.ab20075908.hillforts.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import kotlinx.android.synthetic.main.activity_hillfort.*
import org.ab20075908.hillforts.R
import org.ab20075908.hillforts.helpers.readImage
import org.ab20075908.hillforts.helpers.readImageFromPath
import org.ab20075908.hillforts.models.HillfortModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.ab20075908.hillforts.main.MainApp
import org.ab20075908.hillforts.helpers.showImagePicker
import org.ab20075908.hillforts.models.Location
import org.jetbrains.anko.intentFor

class HillfortActivity : AppCompatActivity(), AnkoLogger {

    var hillfort = HillfortModel()
    lateinit var app: MainApp
    val IMAGE_REQUEST = 1
    //IMAGE_NO is used to determine which of 4 image slots are added to.
    //Image MUST be in first slot to show up as a thumbnail on listview
    var IMAGE_NO = 1
    val LOCATION_REQUEST = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hillfort)
        toolbarAdd.title = title
        setSupportActionBar(toolbarAdd)
        info("Hillfort Activity started..")

        app = application as MainApp

        var edit = false

        //Set up hillfort for editing, including all 4 images and checkbox to edit.

        if (intent.hasExtra("hillfort_edit")) {
            edit = true
            hillfort = intent.extras?.getParcelable<HillfortModel>("hillfort_edit")!!
            hillfortTitle.setText(hillfort.title)
            description.setText(hillfort.description)
            btnAdd.setText(R.string.save_hillfort)
            checkbox_visited.isChecked = hillfort.visited
            hillfortImage1.setImageBitmap(readImageFromPath(this, hillfort.image1))
            hillfortImage2.setImageBitmap(readImageFromPath(this, hillfort.image2))
            hillfortImage3.setImageBitmap(readImageFromPath(this, hillfort.image3))
            hillfortImage4.setImageBitmap(readImageFromPath(this, hillfort.image4))
        }

        //Listener for button to add hillfort

        btnAdd.setOnClickListener() {
            hillfort.title = hillfortTitle.text.toString()
            hillfort.description = description.text.toString()
            if (hillfort.title.isEmpty()) {
                toast(R.string.enter_hillfort_title)
            } else {
                if (edit) {
                    info("SAVING EDIT FOR HILLFORT")
                    app.hillforts.update(hillfort.copy())
                } else {
                    info("SAVING CREATION FOR HILLFORT")
                    app.hillforts.create(hillfort.copy())
                }
            }
            info("add Button Pressed: $hillfortTitle")
            setResult(AppCompatActivity.RESULT_OK)
            finish()
        }




        //Listener for image picker

        chooseImage.setOnClickListener {
            showImagePicker(this, IMAGE_REQUEST)
        }

        //Listener for decrementing image number to be selected by imagepicker

        imageLeft.setOnClickListener {
            info("DECREMENT LISTENER")
            decrementImageSelected()
        }

        //Listener for incrementing image number to be selected by imagepicker

        imageRight.setOnClickListener {
            info("INCREMENT LISTENER")
            incrementImageSelected()
        }


        //Image picker for location

        hillfortLocation.setOnClickListener {
            val location = Location(52.245696, -7.139102, 15f)
            if (hillfort.zoom != 0f) {
                location.lat =  hillfort.lat
                location.lng = hillfort.lng
                location.zoom = hillfort.zoom
            }
            startActivityForResult(intentFor<MapActivity>().putExtra("location", location), LOCATION_REQUEST)
        }

        //Listener for checkbox for having visited location

        checkbox_visited.setOnClickListener()
        {
           onCheckboxClicked(checkbox_visited)
        }


    }

    //Sets up menu bar at the top

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_hillfort, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //Allows user to click items on menu bar at top

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.item_cancel -> {
                finish()
            }
            R.id.item_delete -> {
                app.hillforts.delete(hillfort)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Checkbox for having visited the location

     fun onCheckboxClicked(view: View) {
        if (view is CheckBox) {
            val checked: Boolean = view.isChecked

            info("CHECKBOX")
            info("VISITED STATE = $hillfort.visited")
            when (view.id) {
                R.id.checkbox_visited -> {
                    if (checked) {
                        info("CHECKBOX TRUE")
                        hillfort.visited = true
                    } else {
                        info("CHECKBOX FALSE")
                        hillfort.visited = false
                    }

                    info("VISITED STATE = $hillfort.visited")
                }
            }
        }
    }


    //Increments number of which image will be set by imagepicker

    fun incrementImageSelected()
    {
        info("INCREMENTING")
        info("INCREMENTING = $IMAGE_NO")
        if(IMAGE_NO < 4)
        {
            IMAGE_NO++;
            imageNo.setText("$IMAGE_NO");
        }
        info("INCREMENTING = $IMAGE_NO")
    }

    //Decrements number of which image will be set by imagepicker

    fun decrementImageSelected()
    {
        info("DECREMENTING")
        info("DECREMENTING = $IMAGE_NO")
        if(IMAGE_NO > 1)
        {
            IMAGE_NO--;
            imageNo.setText("$IMAGE_NO");
        }
        info("DECREMENTING = $IMAGE_NO")
    }

    //Depending on number set, will direct the imagepicker to place an image in
    //one of four slots.

    //Also used for location setting as seen in Placemark.

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            IMAGE_REQUEST -> {
                if (data != null) {
                    when(IMAGE_NO) {
                        1 -> {
                            hillfort.image1 = data.getData().toString()
                            hillfortImage1.setImageBitmap(readImage(this, resultCode, data))
                            //chooseImage.setText(R.string.change_hillfort_image)
                        }

                        2 -> {
                            hillfort.image2 = data.getData().toString()
                            hillfortImage2.setImageBitmap(readImage(this, resultCode, data))
                            //chooseImage.setText(R.string.change_hillfort_image)
                        }

                        3 -> {
                            hillfort.image3 = data.getData().toString()
                            hillfortImage3.setImageBitmap(readImage(this, resultCode, data))
                            //chooseImage.setText(R.string.change_hillfort_image)
                        }

                        4 -> {
                            hillfort.image4 = data.getData().toString()
                            hillfortImage4.setImageBitmap(readImage(this, resultCode, data))
                            //chooseImage.setText(R.string.change_hillfort_image)
                        }
                    }
                }
            }
            LOCATION_REQUEST -> {
                if (data != null) {
                    val location = data.extras?.getParcelable<Location>("location")!!
                    hillfort.lat = location.lat
                    hillfort.lng = location.lng
                    hillfort.zoom = location.zoom
                }
            }
        }
    }
}