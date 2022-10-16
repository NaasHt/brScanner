package jp.casio.ht.devicelibrary.scansample.symbolscan


import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import java.io.IOException
import java.io.InputStream


class LogFile:AppCompatActivity (){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_log_file)
        val buttonClick = findViewById<Button>(R.id.btnBack)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val myTextView5= findViewById<TextView>(R.id.textView5)
        val myOutput: String
        val myInputStream: InputStream
        try{
            myInputStream = assets.open("likuciai_ex.txt")
            val size:Int =myInputStream.available()
            val buffer = ByteArray(size)
            myInputStream.read(buffer)
            myOutput=String(buffer)
            myTextView5.text=myOutput
        }catch(e: IOException){
            e.printStackTrace()
        }
    }

}