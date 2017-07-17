package florian_haas.lucas.web.exporter;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.function.Function;

import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;

import org.primefaces.model.*;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.property.AreaBreakType;

import florian_haas.lucas.business.EmploymentBeanLocal;
import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.EnumQueryComparator;
import florian_haas.lucas.web.converter.EnumConverter;
import florian_haas.lucas.web.util.WebUtils;
import net.sourceforge.barbecue.*;

public class IdCardExporter {

	private Float marginTop;
	private Float marginLeft;
	private Float width;
	private Float height;
	private Integer rows;
	private Integer columns;
	private Float rowSpace;
	private Float columnSpace;
	private BufferedImage imageIdCard;
	private BufferedImage backgroundImageIdCard;
	private Integer backgroundColor;
	private Rectangle nameRectPX;
	private Rectangle classRectPX;
	private Rectangle ranksRectPX;
	private Rectangle barcodeRectPX;
	private Rectangle imageRectPX;
	private Character bulletPointChar;
	private Integer maxRanksCount;
	private Integer fontSize;
	private String fontName;
	private Map<ReadOnlyUser, Set<ReadOnlyIdCard>> data = new HashMap<>();
	private Boolean firstPageGenerated = Boolean.FALSE;
	private Rectangle noRanksRectPX;
	private Function<ReadOnlyUser, byte[]> userImageGetter;

	public IdCardExporter(float marginTopMM, float marginLeftMM, Dimension idCardDimensions, Integer rows, Integer columns, Integer rowSpaceMM,
			Integer columnSpaceMM, BufferedImage imageIdCard, BufferedImage backgroundImageIdCard, Integer backgroundColor, Integer nameColor,
			Rectangle nameRectPX, Integer classColor, Rectangle classRectPX, Integer ranksColor, Rectangle ranksRectPX, Rectangle noRanksRectPX,
			Integer barcodeColor, Rectangle barcodeRectPX, Integer imageColor, Rectangle imageRectPX, Integer foregroundColor,
			Character bulletPointChar, Integer maxRanksCount, Integer fontColor, String fontName, Integer fontSize,
			Map<ReadOnlyUser, Set<ReadOnlyIdCard>> data, Function<ReadOnlyUser, byte[]> userImageGetter) {
		this.noRanksRectPX = noRanksRectPX;
		this.userImageGetter = userImageGetter;
		this.marginLeft = getMillimetersAsUnits(marginLeftMM);
		this.marginTop = getMillimetersAsUnits(marginTopMM);
		this.width = getMillimetersAsUnits(idCardDimensions.width);
		this.height = getMillimetersAsUnits(idCardDimensions.height);
		this.rows = rows;
		this.columns = columns;
		this.imageIdCard = imageIdCard;
		this.backgroundImageIdCard = backgroundImageIdCard;
		this.backgroundColor = backgroundColor;
		this.columnSpace = getMillimetersAsUnits(columnSpaceMM);
		this.rowSpace = getMillimetersAsUnits(rowSpaceMM);
		this.nameRectPX = nameRectPX;
		this.classRectPX = classRectPX;
		this.ranksRectPX = ranksRectPX;
		this.barcodeRectPX = barcodeRectPX;
		this.imageRectPX = imageRectPX;
		this.bulletPointChar = bulletPointChar;
		this.maxRanksCount = maxRanksCount;
		this.fontName = fontName;
		this.fontSize = fontSize;
		this.data.putAll(data);
	}

	public static final double CM_INCH = 2.54f;
	public static final Integer UNIT_INCH = 72;
	public static final double CM_UNIT = CM_INCH / UNIT_INCH;
	public static final double MM_UNIT = CM_UNIT * 10;

	public static float getMillimetersAsUnits(float mm) {
		return (float) (mm / MM_UNIT);
	}

	public StreamedContent exportPdf() throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter writer = new PdfWriter(out);
		PdfDocument pdf = new PdfDocument(writer);
		Document document = new Document(pdf, PageSize.A4);
		java.util.List<Map.Entry<ReadOnlyUser, ReadOnlyIdCard>> dataCache = new ArrayList<>();
		for (Map.Entry<ReadOnlyUser, Set<ReadOnlyIdCard>> dataEntry : data.entrySet()) {
			for (ReadOnlyIdCard idCard : dataEntry.getValue()) {
				Map.Entry<ReadOnlyUser, ReadOnlyIdCard> entry = new AbstractMap.SimpleEntry<ReadOnlyUser, ReadOnlyIdCard>(dataEntry.getKey(), idCard);
				dataCache.add(entry);
				if (dataCache.size() == rows * columns) {
					handleUsersCache(document, dataCache);
				}
			}
		}
		if (!dataCache.isEmpty()) {
			handleUsersCache(document, dataCache);
		}
		document.close();
		return new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), WebUtils.PDF_MIME, "idCards.pdf");
	}

	private void handleUsersCache(Document document, java.util.List<Map.Entry<ReadOnlyUser, ReadOnlyIdCard>> dataCache) throws Exception {
		if (firstPageGenerated) {
			document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		} else {
			firstPageGenerated = Boolean.TRUE;
		}
		ArrayList<com.itextpdf.layout.element.Image> images = new ArrayList<>();
		for (Map.Entry<ReadOnlyUser, ReadOnlyIdCard> dataEntry : dataCache) {
			images.add(getIdCardForeground(dataEntry));
		}
		fillPage(document, images);
		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		com.itextpdf.layout.element.Image bg = getIdCardBackground();
		images.replaceAll(img -> bg);
		fillPage(document, images);
		dataCache.clear();
	}

	private void fillPage(Document document, java.util.List<com.itextpdf.layout.element.Image> images) {
		Integer row = 0;
		Integer column = 0;
		for (com.itextpdf.layout.element.Image img : images) {
			float hSpace = marginLeft + column * columnSpace + column * width;
			float vSpace = marginTop + row * rowSpace + row * height + row;
			img.setFixedPosition(hSpace, vSpace);
			document.add(img);
			++column;
			if (column + 1 > columns) {
				column = 0;
				++row;
			}
			if (row + 1 > rows) {
				row = 0;
				column = 0;
			}
		}
	}

	private EmploymentBeanLocal bean;

	public void setBean(EmploymentBeanLocal bean) {
		this.bean = bean;
	}

	private com.itextpdf.layout.element.Image getIdCardForeground(Map.Entry<ReadOnlyUser, ReadOnlyIdCard> dataEntry) throws Exception {
		ReadOnlyUser user = dataEntry.getKey();
		ReadOnlyIdCard userCard = dataEntry.getValue();
		BufferedImage image = WebUtils.deepCopy(imageIdCard);
		Graphics2D renderer = image.createGraphics();
		renderer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderer.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		renderer.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		Barcode barcode = BarcodeFactory.createCode128(Long.toString(userCard.getId()));
		barcode.setResolution(300);
		barcode.setFont(renderer.getFont());
		BufferedImage barcodeImg = new BufferedImage(barcode.getWidth(), barcode.getHeight() + 14, BufferedImage.TYPE_INT_RGB);
		Graphics2D imageRenderer = barcodeImg.createGraphics();
		barcode.draw(imageRenderer, 0, 0);
		imageRenderer.dispose();
		Font font = new Font(fontName, Font.BOLD, fontSize);
		renderer.setFont(font);
		String fullName = user.getForename() + " " + user.getSurname();
		Dimension coordsName = alignStringLeft(nameRectPX, fullName, renderer);
		renderer.setClip(nameRectPX);
		renderer.drawString(fullName, coordsName.width, coordsName.height);
		String classString = WebUtils.getAsString(user.getSchoolClass(), EnumConverter.CONVERTER_ID) + " (" + getCountry(user.getSchoolClass()) + ")";
		Dimension coordsClass = alignStringLeft(classRectPX, classString, renderer);
		renderer.setClip(classRectPX);
		renderer.drawString(classString, coordsClass.width, coordsClass.height);
		int ranksHeight = ranksRectPX.height / maxRanksCount;

		List<String> ranks = new ArrayList<>();
		ranks.addAll(user.getRanks());

		List<? extends ReadOnlyEmployment> e = bean.findEmployments(0l, user.getId(), 0l, new HashSet<>(), false, true, false, false,
				EnumQueryComparator.EQUAL, EnumQueryComparator.EQUAL, EnumQueryComparator.EQUAL, EnumQueryComparator.MEMBER_OF);
		e.forEach(em -> {
			if (em.getJob().getCompany().getCompanyType() == EnumCompanyType.CIVIL
					&& em.getJob().getEmployeePosition() == EnumEmployeePosition.MANAGER) {
				ranks.add("Betriebsleiter");
			}
		});

		if (ranks.isEmpty()) {
			renderer.setClip(noRanksRectPX);
			Dimension coordsNoRanks = alignStringLeft(noRanksRectPX, "---", renderer);
			renderer.drawString("---", coordsNoRanks.width, coordsNoRanks.height);
		} else {
			for (int i = 0; i < maxRanksCount && i < ranks.size(); i++) {
				String rank = bulletPointChar.charValue() + " " + ranks.get(i);
				Rectangle rankRect = new Rectangle(ranksRectPX.x, ranksRectPX.y + i * ranksHeight, ranksRectPX.width, ranksHeight);
				renderer.setClip(rankRect);
				Dimension coordsFirstRank = alignStringLeft(rankRect, rank, renderer);
				renderer.drawString(rank, coordsFirstRank.width, coordsFirstRank.height);
			}
		}
		renderer.setClip(barcodeRectPX);
		renderer.drawImage(barcodeImg, barcodeRectPX.x, barcodeRectPX.y, barcodeRectPX.width, barcodeRectPX.height, null);
		renderer.setClip(imageRectPX);
		byte[] userImage = userImageGetter.apply(user);
		renderer.drawImage(
				ImageIO.read((userImage != null ? new ByteArrayInputStream(userImage)
						: FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/WEB-INF/resources/images/user_male.png"))),
				imageRectPX.x, imageRectPX.y, imageRectPX.width, imageRectPX.height, null);
		renderer.dispose();
		return getScaledITextImageFromBufferesImageWithBGColor(image, backgroundColor, width, height);
	}

	private String getCountry(EnumSchoolClass schoolClass) {
		String country = "None";
		if (schoolClass == null) return "Mond";
		switch (schoolClass) {
			case A10:
				country = "Vereinigtes Königreich";
				break;
			case A11:
				country = "Italien";
				break;
			case A12:
				break;
			case A5:
				country = "Südkorea";
				break;
			case A6:
				country = "Ägypten";
				break;
			case A7:
				country = "Australien";
				break;
			case A8:
				country = "Kolumbien";
				break;
			case A9:
				country = "Jamaika";
				break;
			case B10:
				country = "Deutschland";
				break;
			case B11:
				country = "Frankreich";
				break;
			case B12:
				break;
			case B5:
				country = "China";
				break;
			case B6:
				country = "Nigeria";
				break;
			case B7:
				country = "Papua-Neuguinea";
				break;
			case B8:
				country = "Chile";
				break;
			case B9:
				country = "USA";
				break;
			case C10:
				country = "Norwegen";
				break;
			case C11:
				country = "Portugal";
				break;
			case C12:
				break;
			case C5:
				country = "Indonesien";
				break;
			case C6:
				country = "Südafrika";
				break;
			case C7:
				country = "Fidschi";
				break;
			case C8:
				country = "Brasilien";
				break;
			case C9:
				country = "Mexiko";
				break;
			case D10:
				country = "Belgien";
				break;
			case D11:
				country = "Spanien";
				break;
			case D12:
				break;
			case D5:
				country = "Indien";
				break;
			case D6:
				country = "Kongo";
				break;
			case D7:
				country = "Neuseeland";
				break;
			case D8:
				country = "Uruguay";
				break;
			case D9:
				country = "Kanada";
				break;
			case E10:
				break;
			case E11:
				country = "Griechenland";
				break;
			case E12:
				break;
			case E5:
				country = "Japan";
				break;
			case E6:
				break;
			case E7:
				break;
			case E8:
				country = "Argentinien";
				break;
			case E9:
				break;
			default:
				break;

		}
		return country;
	}

	public static Rectangle ofCoords(int x1, int y1, int x2, int y2) {
		return new Rectangle(x1, y1, x2 - x1, y2 - y1);
	}

	public static Dimension alignStringLeft(Rectangle rect, String string, Graphics2D g2D) {
		TextLayout layout = new TextLayout(string, g2D.getFont(), g2D.getFontRenderContext());
		Dimension ret = new Dimension(rect.x,
				(int) (rect.y + ((rect.getMaxY() + 1 - rect.y) / 2.0f) - ((layout.getAscent() + layout.getDescent()) / 2.0f) + layout.getAscent()));
		return ret;
	}

	private com.itextpdf.layout.element.Image bgImage = null;

	private com.itextpdf.layout.element.Image getIdCardBackground() throws Exception {
		if (bgImage == null) bgImage = getScaledITextImageFromBufferesImageWithBGColor(backgroundImageIdCard, backgroundColor, width, height);
		return bgImage;
	}

	public static com.itextpdf.layout.element.Image getScaledITextImageFromBufferesImageWithBGColor(BufferedImage img, Integer backgroundColor,
			float width, float height) throws Exception {
		com.itextpdf.layout.element.Image ret = new com.itextpdf.layout.element.Image(
				ImageDataFactory.create(WebUtils.convertBufferedImageToBytes(img, WebUtils.PNG_TYPE)));
		ret.scaleAbsolute(width, height);
		return ret;
	}

	public static Color getRgbColorFromInt(Integer rgb) {
		java.awt.Color color = new java.awt.Color(rgb);
		return new DeviceRgb(color.getRed(), color.getGreen(), color.getBlue());
	}
}
