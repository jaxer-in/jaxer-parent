package in.jaxer.core.utilities;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Shakir Ansari
 */
@Getter
@Setter
@ToString
public class QRCodeHandler
{
	private String qrData;
	private String fileLocation;
	private BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

	@Getter(value = AccessLevel.NONE)
	@Setter(value = AccessLevel.NONE)
	private Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
	private int width = 200;
	private int height = 200;

	private void init()
	{
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
	}

	public QRCodeHandler()
	{
		init();
	}

	public QRCodeHandler(String qrData, String fileLocation)
	{
		init();
		this.qrData = qrData;
		this.fileLocation = fileLocation;
	}

	public static BufferedImage getQRCodeBufferedImage(String text, int size) throws Exception
	{
		final Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
		hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size, hintMap);

		return MatrixToImageWriter.toBufferedImage(bitMatrix);
	}

	public void createQRCode() throws WriterException, IOException
	{
		BitMatrix matrix = new MultiFormatWriter().encode(
				new String(qrData.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8),
				barcodeFormat,
				width,
				height,
				hintMap
		);
		MatrixToImageWriter.writeToPath(
				matrix,
				fileLocation.substring(fileLocation.lastIndexOf('.') + 1),
				Paths.get(fileLocation)
		);
	}

	public String readQRCode() throws IOException, NotFoundException
	{
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(fileLocation)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, (Map) hintMap);
		return qrCodeResult.getText();
	}
}
