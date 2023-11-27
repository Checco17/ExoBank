package it.exobank.document;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;

import it.exobank.model.ContoCorrente;
import it.exobank.model.Transazione;
import it.exobank.model.Utente;
import it.exobank.utils.Costanti;
import it.exobank.methods.Utility;
import it.exobank.DTO.*;

/*
 * Estendo la classe PdfPageEventHelper per potermi sovrascrivere il metodo onEndPage() per le gestione degli eventi a fine pagina
 */

public class CreatePdf extends PdfPageEventHelper {

	public byte[] createPdfTable(Dto<List<Transazione>> transazioni) throws Exception{

		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		try {

			PdfWriter writer = PdfWriter.getInstance(document, outputStream);         // Creo l'oggetto PdfWrite responsabile della scrittura del documento PDF

			document.setMargins(30f, 30f, 30f, 60f);                                  // Imposto i margini laterali del documento PDF          

			document.open();

			createParagraph(document, CostantiDocumento.EXOBANK, Element.ALIGN_LEFT,
					new Font(Font.ITALIC, 22, Font.BOLD | Font.ITALIC | Font.UNDERLINE));                     // Creo il paragrafo del titolo del documento 

			createParagraphWithInfo(document, Element.ALIGN_RIGHT, transazioni.getData(),
					new Font(Font.TIMES_ROMAN, 13, Font.BOLD), new Font(Font.TIMES_ROMAN, 12, Font.NORMAL));  //Creo il paragrafo con le informazioni del titolare 

			createParagraph(document, CostantiDocumento.RIEPILOGO_TRANSAZIONE, Element.ALIGN_CENTER,
					new Font(Font.TIMES_ROMAN, 18, Font.BOLD));                                               // Creo il paragrafodi intestazione della tabella 

			PdfPTable table = new PdfPTable(5);                      // Creo un oggetto PdfPTable impostando nel costruttore il numero delle colonne
			table.setWidthPercentage(100); 

			float[] columnWidths = { 80f, 80f, 80f, 80f, 80f };      // Imposto l larghezze di ogni colonna
			table.setWidths(columnWidths);
			

			String[] headerCells = CostantiDocumento.HEADER_CELL_DOC;

			fillHeaderCell(new Font(Font.HELVETICA, 10), headerCells, table, new Color(125, 124, 124));    // Popolo le celle d intestazione

			fillCell(transazioni.getData(), table, new Font(Font.HELVETICA, 8), new Color(247, 247, 247), new Color(212, 210, 210), new Color(255, 0, 0), new Color(47, 156, 8));           // Popolo le celle della tabella con i dati dellla mia lista 
			
			createFooterWithNumbPage(writer, document);                          // Creo il footer con il numero delle pagine corrispondente 

			document.add(table);                                                 // Aggiungo l'oggetto PdfPTable al mio document

			document.close();
			
			
			return outputStream.toByteArray();
		
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new Exception("Errore nella creazione del file PDF");
		}

		
	}

	private Paragraph createParagraph(Document document, String text, int alignment, Font font) {
		Paragraph customText = new Paragraph(text, font);
		customText.setAlignment(alignment);
		document.add(customText);
		document.add(new Paragraph(" "));
		return customText;
	}

	/*
	 * Chunk --> Rappresenta un frammento di testo o contenuto all'interno di un documento PDF (al pari di un Run per Word)
	 */
	
	private void createParagraphWithInfo(Document document, int alignment, List<Transazione> transazioniList,
			Font titleFont, Font textFont) throws DocumentException {

		Chunk titleChunk = new Chunk(CostantiDocumento.DETTAGLI_TITOLARE, titleFont);
		Paragraph titleParagraph = new Paragraph(titleChunk);
		titleParagraph.setAlignment(alignment);
		document.add(titleParagraph);

		Utente utenteModel = transazioniList.get(0).getContoCorrente().getUtente();
		ContoCorrente contoModel = transazioniList.get(0).getContoCorrente();

		Paragraph infoParagraph = new Paragraph();
		infoParagraph.setAlignment(alignment);
		infoParagraph.add(new Chunk(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[0] + utenteModel.getNome(), textFont));
		infoParagraph.add(Chunk.NEWLINE);
		infoParagraph.add(new Chunk(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[1] + utenteModel.getCognome(), textFont));
		infoParagraph.add(Chunk.NEWLINE);
		infoParagraph.add(new Chunk(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[2] + contoModel.getNumeroConto(), textFont));
		infoParagraph.add(Chunk.NEWLINE);
		infoParagraph.add(new Chunk(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[3] + String.format("%.2f€", contoModel.getSaldo()), textFont));
		infoParagraph.add(Chunk.NEWLINE);
		infoParagraph.add(new Chunk(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[4] + LocalDate.now().toString(), textFont));
		document.add(infoParagraph);
		document.add(new Paragraph(" "));
	}
	
	/*
	 * Questo metodo è chiamato automaticamente dall'applicazione durante la creazione del PDF quando si verifica l'evento di fine pagina 
	 * PdfWriter --> oggetto che rapprensenta lo scrittore di un documento PDF
	 * PdfContentByte --> Utilizzatp per scrivere contenuti direttamente sulla pagina PDF, lo ottengo infatti tramite il metodo getDirectContent() 
	 * Phrase -->  oggetto utilizzato per rappresentare una sequenza di testo con proprietà di formattazione specifiche
	 * ColumnText --> Utilizzato per posizionare e disegnare il footer all'interno delpiè di pagina
	 */

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		Font font = new Font(Font.HELVETICA, 6);
		Phrase footer = new Phrase(CostantiDocumento.FOOTER, font);
		footer.add(new Phrase(" - Pagina " + writer.getPageNumber(), font));
		ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, document.left() + 270, document.bottom() - 20, 0);		
	}
	
	
	private void createFooterWithNumbPage(PdfWriter writer, Document document) {		
		CreatePdf pdf = new CreatePdf();		
		pdf.onEndPage(writer, document);		
		writer.setPageEvent(pdf);		
	}
	
	/*
	 * Metodo privato per la crezione e configurazione di un oggetto PdfPCell
	 */
	
	private PdfPCell createCell(String text, Font fontCell, int height, Color backGroundColor) {		
		PdfPCell cell = new PdfPCell(new Paragraph(text, fontCell));
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setBackgroundColor(backGroundColor);
		cell.setFixedHeight(height);
		return cell;	
	}
	
	/*
	 * Metodo privato per popolare le celle d'intestazione dell'oggetto PdfPTable tramite il metodo addCell()
	 */

	private void fillHeaderCell(Font cellFont, String[] headerCells, PdfPTable table, Color colore) {
		for (String header : headerCells) {
			table.addCell(createCell(header, cellFont, 17, colore));
		}
	}
	
	/*
	 * Metodo privato per riempire le celle dell'oggetto PdfPTable 
	 * 
	 * LEGGENDA COLORI:
	 * 125, 124, 124 --> COLORE GRIGIO SCURO PER L'INTESTAZIONE
	 * 247, 247, 247 --> COLORE BIANCO PER LE RIGHE
	 * 212, 210, 210 --> COLORE GRIGIO CHIARO PER LE RIGHE
	 * 255, 0, 0 --> COLORE ROSSO PER GLI IMPORTI
	 * 47, 156, 8 --> COLORE VERDE PER GLI IMPORTI  
	 */

	private void fillCell(List<Transazione> transazioni, PdfPTable table, Font dataFont, Color... colori) {		
		for (int i = 0; i < transazioni.size(); i++) {			
			Transazione transazione = transazioni.get(i);			
			Color backgroundColor = i % 2 == 0 ? colori[0] : colori[1];			
			table.addCell(createCell(Utility.formattaData(transazione.getDataTransazione()), dataFont, 14, backgroundColor));
			double importo = (transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_DEPOSITO)
					? transazione.getImporto()
					: -transazione.getImporto();
			Color textColor = importo < 0 ? colori[2] : colori[3];
			table.addCell(createCell(String.format("%.2f€", importo),  new Font(Font.HELVETICA, 8, Font.NORMAL, textColor), 14, backgroundColor));				
			table.addCell(createCell(transazione.getStatoTransazione().getStatoTransazione(), dataFont, 14, backgroundColor));
			table.addCell(createCell(transazione.getTipoTransazione().getTipoTransazione(), dataFont, 14, backgroundColor));

			String numeroContoBeneficiario = (transazione.getContoCorrenteBeneficiario() != null)
					? transazione.getContoCorrenteBeneficiario().getNumeroConto()
					: "---";
			table.addCell(createCell(numeroContoBeneficiario, dataFont, 14, backgroundColor));
		}
	}

}




  
 
 
