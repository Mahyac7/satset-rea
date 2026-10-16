package id.satset.rea

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * A tiny app built on purpose to be reverse engineered for learning.
 *
 * There are THREE different "secrets" hidden in different ways.
 * Your RE challenge: recover each one by decompiling the APK with JADX/REA.
 *
 *   1) A plaintext secret stored in a string field.           (easy)
 *   2) A password checked by comparing against a value that
 *      is split and concatenated at runtime.                  (medium)
 *   3) A flag that is XOR-"encrypted" and decoded at runtime.  (harder)
 */
class MainActivity : AppCompatActivity() {

    // -------- Secret #1: plaintext (easy) --------
    private val easySecret: String = "SATSET{plaintext_is_never_safe}"

    // -------- Secret #2: assembled at runtime (medium) --------
    // The real password never appears as one literal in the source.
    private fun expectedPassword(): String {
        val part1 = "sat"
        val part2 = "set"
        val part3 = "_2026"
        return part1 + part2 + part3 // => "satset_2026"
    }

    // -------- Secret #3: XOR-"encrypted" flag (harder) --------
    // Encrypted bytes; decoded only when the correct password is entered.
    private val xorKey: Int = 0x42
    private val encryptedFlag: IntArray = intArrayOf(
        // "SATSET{x0r_1s_not_crypto}" each byte XOR 0x42
        0x11, 0x03, 0x16, 0x11, 0x07, 0x16, 0x39, 0x3a, 0x72, 0x30,
        0x1d, 0x73, 0x31, 0x1d, 0x2c, 0x2d, 0x36, 0x1d, 0x21, 0x30,
        0x3b, 0x32, 0x36, 0x2d, 0x3f
    )

    private fun decodeFlag(): String {
        val sb = StringBuilder()
        for (b in encryptedFlag) {
            sb.append((b xor xorKey).toChar())
        }
        return sb.toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 96, 48, 48)
            gravity = Gravity.CENTER_HORIZONTAL
        }

        val title = TextView(this).apply {
            text = "Satset RE Lab"
            textSize = 24f
        }

        val hint = TextView(this).apply {
            text = "Enter the password to reveal the flag."
            textSize = 14f
            setPadding(0, 24, 0, 24)
        }

        val input = EditText(this).apply {
            hint = "password"
        }

        val output = TextView(this).apply {
            textSize = 16f
            setPadding(0, 32, 0, 0)
        }

        val button = Button(this).apply {
            text = "Check"
            setOnClickListener {
                if (input.text.toString() == expectedPassword()) {
                    output.text = "Correct! Flag: " + decodeFlag()
                } else {
                    Toast.makeText(
                        this@MainActivity,
                        "Wrong password, keep digging.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        root.addView(title)
        root.addView(hint)
        root.addView(input)
        root.addView(button)
        root.addView(output)
        setContentView(root)
    }
}
