package jp.casio.ht.devicelibrary.scansample.symbolscan


import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import java.io.*


class LogFile:AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_log_file)

        val FILENAME = "likuciai_ex.txt"
        val myTextView5 = findViewById<TextView>(R.id.textView5)
        val myOutput: String
        val myInputStream: InputStream
        try {
            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                Log.i("State", "Yes is readable!");
                return;
            }
            var sdPath = Environment.getExternalStorageDirectory()
            sdPath = File(sdPath.absolutePath + "/Downloads/")
            val sdFile = File(sdPath, FILENAME)
//            try {
//                val reader = BufferedReader(FileReader(sdFile))
//                val size= myInputStream.available()
//                val buffer = ByteArray(size)
//                myInputStream.read(buffer)
//                myOutput = String(buffer)
//                myTextView5.text = myOutput
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
        }catch(e: FileSystemException){
            e.printStackTrace()
        }
    }
}