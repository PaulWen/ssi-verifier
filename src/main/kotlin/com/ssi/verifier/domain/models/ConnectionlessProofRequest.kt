package com.ssi.verifier.domain.models

import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.client.j2se.MatrixToImageWriter
import java.io.ByteArrayOutputStream
import java.util.*


class ConnectionlessProofRequest(
    val exchangeId: String,
    val url: String
) {
    val qrCodeDataUrlEncoded: String

    init {
        val imageSize = 800
        val matrix = MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,
            imageSize, imageSize)
        val bos = ByteArrayOutputStream()
        MatrixToImageWriter.writeToStream(matrix, "png", bos)
        val image: String = Base64.getEncoder().encodeToString(bos.toByteArray())

        qrCodeDataUrlEncoded = "data:image/png;base64,$image"
    }

}
