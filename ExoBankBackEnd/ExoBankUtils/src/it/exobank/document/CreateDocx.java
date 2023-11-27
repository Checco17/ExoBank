package it.exobank.document;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTFldChar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STFldCharType;

import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import java.time.LocalDate;
import it.exobank.DTO.Dto;
import it.exobank.model.ContoCorrente;
import it.exobank.model.Transazione;
import it.exobank.model.Utente;
import it.exobank.utils.Costanti;
import it.exobank.methods.Utility;

/*
 * Apache POI è un insieme di librerie Java che forniscono funzionalità per la manipolazione dei documenti 
 * di Microsoft Office, come fogli di calcolo Excel, documenti Word e presentazioni PowerPoint. 
 */

public class CreateDocx {

	public byte[] createDocxFile(Dto<List<Transazione>> transazioni) throws Exception{

		/*
		 * Definisco un oggetto di tipo XWPFDocument che rappresenta una pagina Word a
		 * cui poi è possibile aggiungere contenut, paragrafi e tabelle
		 */

		try (XWPFDocument document = new XWPFDocument();
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

			createParagraph(document, true, true, 24, CostantiDocumento.STYLE_FAMILY_ROMAN , ParagraphAlignment.LEFT, UnderlinePatterns.SINGLE,
					CostantiDocumento.EXOBANK);

			createParagraphWithInfo(document, ParagraphAlignment.RIGHT, transazioni.getData());

			createParagraph(document, true, false, 18, CostantiDocumento.STYLE_FAMILY_MODERN , ParagraphAlignment.LEFT, UnderlinePatterns.NONE,
					CostantiDocumento.RIEPILOGO_TRANSAZIONE);

			/*
			 * Creo un oggetto di tipo XWPFTable che rappresenta una tabella del mio file
			 * Word ottenuta tramite il metodo createTable() di document
			 */

			XWPFTable table = document.createTable(transazioni.getData().size() + 1, 5);  // Creo un elemento XWPFTable associato al documento, tra parentesi imposto righe e colonne

			table.setWidth("100%");                              // Setto la profondità in percentuale

			XWPFTableRow titoloHeader = table.getRow(0);         // Creo la prima riga del foglio per riempirla con la mia intestazione 

			String[] headers = CostantiDocumento.HEADER_CELL_DOC;

			fillHeaderCell(titoloHeader, headers, CostantiDocumento.COLORE_DOCX_GRIGIO_SCURO);               // Utilizzo il metodo fillHeaderCell() per creare l'intestazione

			fillCell(transazioni.getData(), table, CostantiDocumento.COLORE_DOCX_BIANCO, CostantiDocumento.COLORE_DOCX_GRIGIO_CHIARO, CostantiDocumento.COLORE_DOCX_VERDE, CostantiDocumento.COLORE_DOCX_ROSSO);                    // Utilizzo il metoto fillCell() per popolare le celle con i dati della mia List							

			createFooterWithNumbPage(document, CostantiDocumento.FOOTER); // Mi genero il footer del file Word

			document.write(outputStream);                        // Utilizzo il metoto write() per passare il contenuto del document  al'oggetto outputStream 
            
			return outputStream.toByteArray();                   // Resituisco i dati in formato array di byte
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Errore nella creazione del file Word");
		}
	}

	/*
	 * Definisco un metodo privato per la creazione e configurazione di un oggetto di tipo XWPFParagraph (porzione di testo di un documento separata dagli altri)
	 * tramite il metodo createParagraph() utilizzato dall'oggetto document
	 * che lo associa direttamente al document che possiede una variabile d'istanza List<XWPFParagraph>
	 */
	
	private XWPFParagraph createParagraph(XWPFDocument document, boolean bold, boolean italic, int size, String family, ParagraphAlignment align, UnderlinePatterns underLine, String text) {

		XWPFParagraph paragraph = document.createParagraph();
		paragraph.setAlignment(align);
		XWPFRun run = paragraph.createRun();    // Creo l'oggetto XWPFRun che corrisponde ad una serie di caratteri con lo stesso stile all'interno di un paragrafo
		run.setBold(bold);
		run.setFontSize(size);
		run.setFontFamily(family);
		run.setText(text);
		run.setItalic(italic);
		run.setUnderline(underLine);
		run.addBreak();     // Aggiungo lo spazio
		return paragraph;
	}

	/*
	 * Metodo per la creazione di un paragrafo con le informazioni dell'utente che vede la sua lista
	 * delle transazioni effettuate
	 */
	private void createParagraphWithInfo(XWPFDocument document, ParagraphAlignment align, List<Transazione> transazioniList) {
		XWPFParagraph infoParagraph = document.createParagraph();
		infoParagraph.setAlignment(align);
		XWPFRun infoRun = infoParagraph.createRun();
		infoRun.setFontSize(13);
		Utente utenteModel = transazioniList.get(0).getContoCorrente().getUtente();
		ContoCorrente contoModel = transazioniList.get(0).getContoCorrente();
		infoRun.setBold(true);
		infoRun.setText(CostantiDocumento.DETTAGLI_TITOLARE);
		infoRun.addCarriageReturn();
		XWPFRun infoRun1 = infoParagraph.createRun();
		infoRun1.setFontSize(12);
		infoRun1.setBold(false);
		infoRun1.setText(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[0] + utenteModel.getNome());
		infoRun1.addCarriageReturn();
		infoRun1.setText(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[1] + utenteModel.getCognome());
		infoRun1.addCarriageReturn();
		infoRun1.setText(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[2] + contoModel.getNumeroConto());
		infoRun1.addCarriageReturn();
		infoRun1.setText(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[3] + String.format("%.2f", contoModel.getSaldo()) + "€");
		infoRun1.addCarriageReturn();
		infoRun1.setText(CostantiDocumento.DETTAGLI__TITOLARE_LABEL[4] + LocalDate.now().toString());
		infoRun1.addBreak();
		infoRun1.addBreak();
	}
	
	/*
	 * CTP --> Common Text Properties
	 * CTR --> Common Text Run
	 * Run --> Sequenza di testo con le stesse proprietà di formattazione
	 * Sono entrambe interfacce che estendono XmlObject quindi sono come elementi XML
	 * Politica per l'intestazione e per il footer --> insieme di regole, direttive o oggetti che definiscono come questi 
	 * elementi devono comparire nel documento
	 */

	/*
	 * Definisco un metodo privato per la creazione del footer personalizzato per il documento Word
	 * Creo un oggetto di tipo interfaccia CTP che rappresenta un elemento vuoto di un documento Word utilizzato
	 * per rappresentare un testo o le proprietà del testo da aggiungere, tramite il metodo newInstance() di Factory 
	 * che è un costante di tipo DocumentFactory dell'interfaccia CTP.
	 * 
	 */
	private void createFooterWithNumbPage(XWPFDocument document, String text) {

		CTP ctp = CTP.Factory.newInstance();
		CTR ctr = ctp.addNewR();                       // Creo un oggetto di tipo CTR e lo associo all'elemento CTP tramite il metodo addNewR()
		CTText txt = ctr.addNewT();                    // Aggiungo un elemento di tipo CTText (testo) all'oggetto CTP tramite il metodo addNewT() dell oggetto CTR
		txt.setStringValue(text);                      // Imposto il testo all'interno dell'elemento txt
		txt.setSpace(SpaceAttribute.Space.PRESERVE);   // Imposto l'attributo di spazio al testo

		CTP ctpNumPage = CTP.Factory.newInstance();

		CTText txt1 = ctpNumPage.addNewR().addNewT();
		txt1.setStringValue("Pag. ");
		txt1.setSpace(SpaceAttribute.Space.PRESERVE);

		CTR run = ctpNumPage.addNewR();
		CTFldChar fld = run.addNewFldChar();                    // Creo un oggetto di tipo CTFldChar(rappresenta i campi dei documenti Word) e lo associo all'elemento CTR tramite il metodo addNewFldChar() 
		fld.setFldCharType(STFldCharType.BEGIN);                // Imposto l'attributo FldCharType che indica quale tipo di campo sta iniziando
		run.addNewInstrText().setStringValue(" PAGE ");         // Assegno l'istruzione 'PAGE' all'elemento InstrText che fornisce le informazioni necessarie per definire il comportamento del campo
		run.addNewFldChar().setFldCharType(STFldCharType.END);

		txt1 = ctpNumPage.addNewR().addNewT();
		txt1.setStringValue(" of ");
		txt1.setSpace(SpaceAttribute.Space.PRESERVE);

		run = ctpNumPage.addNewR();
		run.addNewFldChar().setFldCharType(STFldCharType.BEGIN);
		run.addNewInstrText().setStringValue(" NUMPAGES ");
		run.addNewFldChar().setFldCharType(STFldCharType.END);

		XWPFParagraph parFooter = new XWPFParagraph(ctp, document);      // Creo l'oggetto XWPFParagraph passando in parametro il CTP e il XWPFDocument
		parFooter.setSpacingBefore(200);                                 // Imposto lo spazio vuoto prima del paragrafo
		parFooter.setAlignment(ParagraphAlignment.CENTER);               // Imposto l'allineamento del paragrafo

		XWPFParagraph parNumbPage = new XWPFParagraph(ctpNumPage, document);   
		parNumbPage.setAlignment(ParagraphAlignment.RIGHT);

		XWPFHeaderFooterPolicy policy = document.getHeaderFooterPolicy();         // Cerco di ottenere la politica  attuale per l'intestazione e il piè di pagina
		if (policy == null) {
			policy = document.createHeaderFooterPolicy();                         // Nel caso non ci fosse ne creo una nuova
		}
		policy.createFooter(XWPFHeaderFooterPolicy.DEFAULT, new XWPFParagraph[] { parFooter, parNumbPage });     // Creo il piè di pagina impostando la politica DEFAULT e includo due paragrafi per il testo del footer e il numero di pagina
	}
	
	/*
	 * Metodo privato per la creazione e configurazione dell'oggetto XWPFTableRow ovvero le righe associate direttamente alla tabella
	 */

	private XWPFTableRow createTableRow(XWPFTable table, int index, int height) {
		XWPFTableRow row = table.getRow(index);
		row.setHeight(height);
		return row;
	}

	/*
	 * Metodo privato per la creazione  e configurazione dell'oggetto XWPFTableCell ovvero le celle della tabella 
	 */
	
	private XWPFTableCell createTableCell(XWPFTableRow tableRow, int index, String color) {
		XWPFTableCell cell = tableRow.getCell(index);
		cell.setColor(color);
		return cell;
	}
	
	/*
	 * Metodo privato per la creazione e configurazione dell'oggetto XWPFRun ovvero il run associato alla cella 
	 */

	private XWPFRun createTableRun(XWPFTableCell cell, String text, int size, ParagraphAlignment align) {
		XWPFParagraph paragraph = cell.addParagraph();
		paragraph.setAlignment(align);
		XWPFRun run = paragraph.createRun();
		run.setText(text);
		run.setFontSize(size);
		return run;
	}

	/*
	 * Metodo privato per riempire le celle della riga d'intestazione
	 */
	
	private void fillHeaderCell(XWPFTableRow titoloHeader, String[] headers, String colore) {
		for (int i = 0; i < headers.length; i++) {
			XWPFTableCell cell = createTableCell(titoloHeader, i, colore);
			createTableRun(cell, headers[i], 9, ParagraphAlignment.CENTER);
		}
	}

	/*
	 * Metodo privato per popolare le celle della tabella con i dati della List<Transazione>
	 */
	
	private void fillCell(List<Transazione> transazioniList, XWPFTable table, String... colori) {
		for (int i = 0; i < transazioniList.size(); i++) {
			String color = (i % 2 == 0) ? colori[0] : colori[1];
			XWPFTableRow row = createTableRow(table, i + 1, 10);
			XWPFTableCell cell0 = createTableCell(row, 0, color);
			XWPFRun run = createTableRun(cell0, Utility.formattaData(transazioniList.get(i).getDataTransazione()), 8,
					ParagraphAlignment.CENTER);
			XWPFTableCell cell1 = createTableCell(row, 1, color);
			run = createTableRun(cell1,
					String.format("%.2f€",
							(transazioniList.get(i).getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_DEPOSITO)
									? transazioniList.get(i).getImporto()
									: -transazioniList.get(i).getImporto()),
					8, ParagraphAlignment.CENTER);
			run.setColor((transazioniList.get(i).getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_DEPOSITO)
					? colori[2]
					: colori[3]);
			XWPFTableCell cell2 = createTableCell(row, 2, color);
			run = createTableRun(cell2, transazioniList.get(i).getStatoTransazione().getStatoTransazione(), 8,
					ParagraphAlignment.CENTER);
			XWPFTableCell cell3 = createTableCell(row, 3, color);
			run = createTableRun(cell3, transazioniList.get(i).getTipoTransazione().getTipoTransazione(), 8,
					ParagraphAlignment.CENTER);
			XWPFTableCell cell4 = createTableCell(row, 4, color);
			run = createTableRun(cell4,
					(transazioniList.get(i).getContoCorrenteBeneficiario() != null)
							? transazioniList.get(i).getContoCorrenteBeneficiario().getNumeroConto()
							: "---",
					8, ParagraphAlignment.CENTER);

		}

	}
}
