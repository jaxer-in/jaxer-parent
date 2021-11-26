
package in.jaxer.core.utilities;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import in.jaxer.core.constants.ContentType;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 *
 * @author Shakir Ansari
 */
public class QRCodeHandler
{

	private String qrData;

	private String fileLocation;

	private BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;

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
		BitMatrix matrix = new MultiFormatWriter().encode(new String(qrData.getBytes(ContentType.UTF_8), ContentType.UTF_8), barcodeFormat, width, height, hintMap);
		MatrixToImageWriter.writeToFile(matrix, fileLocation.substring(fileLocation.lastIndexOf('.') + 1), new File(fileLocation));
	}

	public String readQRCode() throws FileNotFoundException, IOException, NotFoundException
	{
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(fileLocation)))));
		Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, (Map) hintMap);
		return qrCodeResult.getText();
	}

	public Map<EncodeHintType, ErrorCorrectionLevel> getHintMap()
	{
		return hintMap;
	}

	public void setHintMap(Map<EncodeHintType, ErrorCorrectionLevel> hintMap)
	{
		this.hintMap = hintMap;
	}

	public String getQrData()
	{
		return qrData;
	}

	public void setQrData(String qrData)
	{
		this.qrData = qrData;
	}

	public String getFileLocation()
	{
		return fileLocation;
	}

	public void setFileLocation(String fileLocation)
	{
		this.fileLocation = fileLocation;
	}

	public int getWidth()
	{
		return width;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public int getHeight()
	{
		return height;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public BarcodeFormat getBarcodeFormat()
	{
		return barcodeFormat;
	}

	public void setBarcodeFormat(BarcodeFormat barcodeFormat)
	{
		this.barcodeFormat = barcodeFormat;
	}
}
