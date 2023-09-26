package vn.liam.codebase.utils

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader


object FileReader {

    private const val ASSET_BASE_PATH = "../app/src/main/assets/"

    fun readStringFromFile(fileName: String): String {
        try {
            val br = BufferedReader(InputStreamReader(FileInputStream(ASSET_BASE_PATH + fileName)))
            val sb = StringBuilder()
            var line = br.readLine()
            while (line != null) {
                sb.append(line)
                line = br.readLine()
            }

            return sb.toString()
        } catch (e: IOException) {
            throw e
        }
    }
}