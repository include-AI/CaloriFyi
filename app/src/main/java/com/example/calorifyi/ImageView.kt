package com.example.calorifyi

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
    import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.example.calorifyi.ui.theme.CaloriFyiTheme
import java.io.File

class ImageView : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloriFyiTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    requestPermissions(LocalContext.current, this)
                    gridView(LocalContext.current)
                }
            }
        }
    }
}

private fun checkPermission(ctx: Context): Boolean {
    // in this method we are checking if the permissions are granted or not and returning the result.
    val result = ContextCompat.checkSelfPermission(ctx, READ_EXTERNAL_STORAGE)
    return result == PackageManager.PERMISSION_GRANTED
}

private fun requestPermissions(ctx: Context, activity: Activity) {
    if (checkPermission(ctx)) {
        Toast.makeText(ctx, "Permissions granted", Toast.LENGTH_SHORT).show()
        getImagePath(ctx)
    } else {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(READ_EXTERNAL_STORAGE), 101
        )
    }
}

private fun getImagePath(ctx: Context): MutableList<String> {

    val imgList: MutableList<String> = ArrayList()
    val isSDPresent = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    if (isSDPresent) {
        // if the sd card is present we are creating a new list in
        // which we are getting our images data with their ids.
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.ImageColumns.DATE_TAKEN)
        // on below line we are creating a new
        // string to order our images by string.
        val orderBy = MediaStore.Images.Media.DATE_TAKEN + " DESC"
        // this method will stores all the images
        // from the gallery in Cursor
        val cursor: Cursor? = ctx.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            columns,
            null,
            null,
            orderBy
        )
        // below line is to get total number of images
        val count: Int = cursor!!.count
        // on below line we are running a loop to add
        // the image file path in our array list.
        for (i in 0 until count) {
            // we will be only displaying 10 images
            // on below line we are breaking the loop
            // when array list is > 10
            if (imgList.size > 500) {
                break
            }
            // on below line we are moving our cursor position
            cursor.moveToPosition(i * 3)
            // on below line we are getting image file path
            val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            // after that we are getting the image file path
            // and adding that path in our array list.
            imgList.add(cursor.getString(dataColumnIndex))
        }
        // after adding the data to our
        // array list we are closing our cursor.
        cursor.close()
    }
    // on below lien we are returning our list
    return imgList
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun gridView(context: Context) {

    var imgList: MutableList<String> = ArrayList()
    imgList = getImagePath(context)

    LazyVerticalGrid(

        columns = GridCells.Fixed(3),

        modifier = Modifier.padding(10.dp)
    ) {

        items(imgList.size) {

            Card(

                onClick = {
                    val i = Intent(context, ImageView2::class.java)
                    i.putExtra("img", imgList[it])
                    context.startActivity(i)
                },
                // on below line we are adding padding from our all sides.
                modifier = Modifier
                    .padding(3.dp)
                    .width(100.dp)
                    .height(100.dp),

                // on below line we are adding elevation for the card.
                elevation = 2.dp
            ) {
                // on below line we are creating a column on below sides.
                Column(
                    // on below line we are adding padding
                    // padding for our column and filling the max size.
                    Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    // on below line we are adding
                    // horizontal alignment for our column
                    horizontalAlignment = Alignment.CenterHorizontally,
                    // on below line we are adding
                    // vertical arrangement for our column
                    verticalArrangement = Arrangement.Center
                ) {
                    // on below line we are creating an image file.
                    val imgFile = File(imgList[it])
                    // on below line we are creating image for our grid view item.
                    Image(
                        // on below line we are specifying the drawable image for our image.
                        //  painter = painterResource(id = courseList[it].languageImg),
                        painter = rememberImagePainter(data = imgFile),
                        // on below line we are specifying
                        // content description for our image
                        contentDescription = "Javascript",
                        // on below line we are setting height
                        // and width for our image.
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}