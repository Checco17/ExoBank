package it.exobank.document;

import java.io.ByteArrayOutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import it.exobank.DTO.Dto;
import it.exobank.model.Transazione;
import it.exobank.utils.Costanti;
import it.exobank.methods.Utility;

/*
 * Apache POI è un insieme di librerie Java che forniscono funzionalità per la manipolazione dei documenti 
 * di Microsoft Office, come fogli di calcolo Excel, documenti Word e presentazioni PowerPoint. 
 */

public class CreateXlsx {
    
    public byte[] createXlsxFile(Dto<List<Transazione>> transazioni) throws Exception{
    	
    	/*
    	 * Definisco un oggetto di tipo WorkBook(interfaccia) tramite l'istanza della classe che la implementa
    	 * XSSFWorkbook; l'oggetto workbook è un contenitore per i dati di Excel e rappresenta  un file Excel
    	 * Utilizzo il blocco try-catch-resource per inizializzare in maniera sicura e gestita l'oggetto workbook
    	 * cosi da non dover gestire la chiusura dell'oggetto dentro il blocco anche in caso di cattura dell'eccezione
    	 * Definisco un oggetto di tipo ByteArrayOutputStream cosi poter memorizzare temporaneamente i dati generati 
    	 * dal Workbook (file Excel) in memoria e ritornarli per il metodo 
    	 */
    	
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
        	
            Sheet foglio = workbook.createSheet(CostantiDocumento.NOME_FOGLIO_XLSX);  // Creo un oggetto di tipo Sheet che rappresenta un foglio all'interno del mio workbook

            CellStyle stileCellaGiallo = createCellStyle(workbook, IndexedColors.YELLOW, FillPatternType.SOLID_FOREGROUND, HorizontalAlignment.CENTER, false, 12);
            CellStyle stileCellaBianco = createCellStyle(workbook, IndexedColors.WHITE, FillPatternType.SOLID_FOREGROUND, HorizontalAlignment.CENTER, false, 12);
            CellStyle stileCellaRosso = createCellStyle(workbook, IndexedColors.RED, FillPatternType.SOLID_FOREGROUND, HorizontalAlignment.CENTER, false, 12);
            CellStyle stileCellaHeaderGrassettoVerde = createCellStyle(workbook, IndexedColors.GREEN, FillPatternType.SOLID_FOREGROUND, HorizontalAlignment.CENTER, true, 14);

            
            Row titoloHeader = foglio.createRow(0);   // Creo la prima riga del foglio per riempirla con la mia intestazione
            String[] headers = CostantiDocumento.HEADER_CELL_XLSX;

            fillHeaderCell(headers, titoloHeader, stileCellaHeaderGrassettoVerde, foglio, 20 );  // Utilizzo il metodo fillHeaderCell() per creare l'intestazione

            List<Transazione> transazioniList = transazioni.getData();
            
            fillCell(foglio, transazioniList, stileCellaGiallo, stileCellaBianco, stileCellaRosso);  // Utilizzo il metoto fillCell() per popolare le celle con i dati della mia List

            workbook.write(outputStream);                // Utilizzo il metoto write() per passare il contenuto del  workbook al'oggetto outputStream 
            
            return outputStream.toByteArray();           // Resituisco i dati in formato array di byte
        
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Errore nella creazione del file Xlsx");
        }
    }
    
    /*
     * Definisco un metodo privato per la creazione di un oggetto di tipo CellStyle che mi servirà per applicare uno
     * stile alle celle del foglio Excel in base ai parametri che gli passo in input cosi da semplificare l'azione
     * Per creare l'oggetto CellStyle bisogna utilizzare il metodo di workbook createCellStyle che restuisce un
     * oggetto CellStyle; stesso discorso per l'oggetto Font che mi servirà per creare stili maggiormente specifici
     * per la mia cella
     */

    private CellStyle createCellStyle(Workbook workbook, IndexedColors backgroundColor, FillPatternType fillPatternType, HorizontalAlignment alignment,boolean bold, int size) {
    	
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        style.setFillForegroundColor(backgroundColor.getIndex());  // Indica il colore di riempimento della cella, getIndex() rappresenta il numero di colore di riferimento
        style.setFillPattern(fillPatternType);    // Indica il modo in cui va riempita la cella
        style.setAlignment(alignment);            // Indica la posizione rall'interno della cella 
        font.setBold(bold);                                        // Indica il font in grassetto
        font.setFontHeightInPoints((short) size);                  // Indica la grandezza delle lettere nella cella 
        style.setFont(font);                                       // Assegno l'oggetto font a style
        return style;
    }
    
    /*
     * Definisco un metodo privato per la creazione di un oggetto di tipo Cell tramite il metodo della classe Row
     * createCell(), in seguito utilizzo setCellValye() per assegnargli un stringa  e setCellStyle() per passargli lo stile adeguato
     * 
     */

    private void createCell(Row row, int columnIndex, String value, CellStyle style) {
    	
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue(value);
        cell.setCellStyle(style);
    }
    
    /*
     * Definisco un metodo privato per popolare l'intestazione del foglio Excel con le celle 
     */
    
    private void fillHeaderCell(String [] headers,  Row titoloHeader, CellStyle stileCellaHeaderGrassettoVerde, Sheet foglio, int height ) {
    	
    	for (int i = 0; i < headers.length; i++) {    		
            createCell(titoloHeader, i, headers[i], stileCellaHeaderGrassettoVerde);   // Utilizzo il mio metodo privato createCell()
            foglio.setColumnWidth(i, 30 * 256);                                        // Setto la profondità della colonna 
            titoloHeader.setHeightInPoints(height);                                        // Setto l'altezza della riga
        }
    	
    }
    
    /*
     * Definisco un metodo privato per popolare le celle con i dati della mia lista di transazioni passata come paramentro
     * assieme agli oggetti di CellStyle per assegnare lo stile alle celle
     */
    
    private void fillCell(Sheet foglio, List<Transazione> transazioniList, CellStyle... stili) {
    	
    	for (int numRiga = 1; numRiga <= transazioniList.size(); numRiga++) {
            Row row = foglio.createRow(numRiga);                                     // Creo la prima riga partendo da 1 e non 0 poichè è gia stata riempita dell'intestazione
            Transazione transazione = transazioniList.get(numRiga -1);               // Invece qui dimuisco di 1 il numRiga per prendere il primo oggetto della mia List
            CellStyle currentStyle = (numRiga % 2 == 0) ? stili[0] : stili[1];

            createCell(row, 0, transazione.getContoCorrente().getUtente().getNome(), currentStyle);
            createCell(row, 1, transazione.getContoCorrente().getUtente().getCognome(), currentStyle);
            createCell(row, 2, transazione.getContoCorrente().getNumeroConto(), currentStyle);
            createCell(row, 3, Utility.formattaData(transazione.getDataTransazione()), currentStyle);

            String importoCell = (transazione.getTipoTransazione().getId() != Costanti.TIPO_TRANSAZIONE_DEPOSITO) ?
                    -transazione.getImporto() + "€" : transazione.getImporto() + "€";
            CellStyle importoCellStyle = (transazione.getTipoTransazione().getId() != Costanti.TIPO_TRANSAZIONE_DEPOSITO) ?
                    stili[2] : currentStyle;
            createCell(row, 4, importoCell, importoCellStyle);

            createCell(row, 5, transazione.getStatoTransazione().getStatoTransazione(), currentStyle);
            createCell(row, 6, transazione.getTipoTransazione().getTipoTransazione(), currentStyle);
            createCell(row, 7, (transazione.getContoCorrenteBeneficiario() != null) ? transazione.getContoCorrenteBeneficiario().getNumeroConto() : "---", currentStyle);
        }
    	
    }
}
