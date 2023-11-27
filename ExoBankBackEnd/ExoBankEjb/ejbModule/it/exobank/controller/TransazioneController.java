package it.exobank.controller;

import it.exobank.DTO.*;
import it.exobank.crud.ContoCorrenteCrud;
import it.exobank.crud.TransazioneCrud;
import it.exobank.model.ContoCorrente;
import it.exobank.model.Transazione;
import it.exobank.sqlmapfactory.SqlMapFactory;
import it.exobank.methods.Convertitore;
import it.exobank.utils.Costanti;
import it.exobank.email.EmailConst;
import it.exobank.email.SendEmail;
import it.exobank.methods.Validatore;
import it.exobank.ejbInterface.TransazioneControllerInterface;
import it.exobank.mapper.ContoCorrenteMapper;
import it.exobank.mapper.TransazioneMapper;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/*
 * INSERISCO OPERAZIONE DI COMMIT E ROLLBACK NONOSTANTE NON SIANO NECESSARIE 
 * POICHE' LE TRANSAZIONE VENGONO GESTITE DAL CONTAINER DI WILD FLY 
 * IN QUANTO HO IMPOSTATO IL TRANSACTIONMANAGER TYPE= MANAGED ANZICHE' JDBC
 */

@Stateless(name = "TransazioneControllerInterface")
@LocalBean
public class TransazioneController implements TransazioneControllerInterface {

	private TransazioneCrud crud = TransazioneCrud.getInstance();

	private ContoCorrenteCrud crudConto = ContoCorrenteCrud.getInstance();

	public TransazioneController() {

	}

	@Override
	public Dto<Transazione> insertTransazione(Transazione transazione) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<Transazione> transazioneDTO = new Dto<Transazione>();
		try {
			TransazioneMapper mapper = factory.getMapper(TransazioneMapper.class);
			if (new Validatore().validatoreTransazione(transazione)) {

				Transazione model = crud.insertTransazione(mapper, transazione);

				if (null != model) {
					Transazione modelDTO = new Convertitore().fromTransazioneToDTO(model);
					transazioneDTO.setData(modelDTO);
					new SendEmail().sendEmail(Costanti.EMAIL_DESTINATARIO, EmailConst.TRANSAZIONE_EFFETTUATA, EmailConst.GET_MESSAGE_TRANSAZIONE_EFFETTUATA(transazione.getContoCorrente().getUtente()), null, null);
					factory.commitSession();
					return transazioneDTO;

				} else {
					factory.rollbackSession();
					System.out.println(
							"Errore presente nella riga 45 nella classe TransazioneController nel metodo insertTransazione");
					throw new Exception("Errore inserimento transazione");
				}

			} else {
				factory.rollbackSession();
				System.out.println(
						"Errore presente nella riga 43 nella classe TransazioneController nel metodo insertTransazione");
				throw new Exception("Errore di validazione della transazione");
			}

		} catch (SQLException s) {
			s.printStackTrace();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);

		} finally {

			factory.closeSession();
		}

	}

	@Override
	public Dto<List<Transazione>> findAllTransazione() throws SQLException, Exception {
		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<List<Transazione>> dto = new Dto<List<Transazione>>();
		try {
			TransazioneMapper mapper = factory.getMapper(TransazioneMapper.class);
			List<Transazione> transazioni = crud.findAllTransazione(mapper);

			if (null != transazioni) {
				List<Transazione> transazioniDTO = new Convertitore().fromListTransazioneToDTO(transazioni);
				dto.setData(transazioniDTO);
				return dto;

			} else {
				System.out.println(
						"Errore presente nella riga 91 nella classe TransazioneController nel metodo findAllTransazione");
				throw new Exception("Non sono presenti transazioni registrate");

			}

		} catch (SQLException s) {
			s.printStackTrace();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);

		} finally {

			factory.closeSession();
		}
	}

	@Override
	public Dto<Transazione> updateTransazione(Transazione transazione) throws SQLException, Exception {
	    SqlMapFactory.instance().openSession();
	    SqlMapFactory factory = SqlMapFactory.instance();
	    Dto<Transazione> transazioneDTO = new Dto<Transazione>();

	    try {
	        TransazioneMapper mapper = factory.getMapper(TransazioneMapper.class);

	        if (new Validatore().validatoreTransazione(transazione)) {
	            Transazione model = crud.updateTransazione(mapper, transazione);

	            if (null != model) {
	                Transazione modelDTO = new Convertitore().fromTransazioneToDTO(model);
	                updateSaldoContoCorrente(modelDTO); //Metodo privato per aggiornare il saldo del conto corrente
	                factory.commitSession();
	                transazioneDTO.setData(modelDTO);
	                return transazioneDTO;
	            } else {
	                factory.rollbackSession();
	                System.out.println("Errore presente nella riga 130 nella classe TransazioneController nel metodo updateTransazione");
	                throw new Exception("Errore modifica transazione");
	            }
	        } else {
	            factory.rollbackSession();
	            System.out.println("Errore presente nella riga 128 nella classe TransazioneController nel metodo updateTransazione");
	            throw new Exception("Errore di validazione della transazione");
	        }
	    } catch (SQLException s) {
	        s.printStackTrace();
	        throw new SQLException(Costanti.CONTATTA_ASSISTENZA);
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);
	    } finally {
	        factory.closeSession();
	    }
	}

	@Override
	public Dto<Transazione> findTransazioneById(Transazione transazione) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<Transazione> transazioneDTO = new Dto<Transazione>();
		try {
			TransazioneMapper mapper = factory.getMapper(TransazioneMapper.class);
			Transazione model = crud.findTransazioneById(mapper, transazione);

			if (null != model) {
				Transazione modelDTO = new Convertitore().fromTransazioneToDTO(model);
				transazioneDTO.setData(modelDTO);
				return transazioneDTO;

			} else {
				System.out.println(
						"Errore presente nella riga 201 nella classe TransazioneController nel metodo findTransazioneById");
				throw new Exception("La ricerca non ha prodotto alcun risultato");
			}

		} catch (SQLException s) {
			s.printStackTrace();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);

		} finally {

			factory.closeSession();
		}
	}

	@Override
	public Dto<List<Transazione>> findTransazioneByUtenteId(Integer utenteId) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<List<Transazione>> transazioneDTO = new Dto<List<Transazione>>();
		try {
			TransazioneMapper mapper = factory.getMapper(TransazioneMapper.class);
			List<Transazione> model = crud.findTransazioneByUtenteId(mapper, utenteId);

			if (null != model) {
				List<Transazione> modelDTO = new Convertitore().fromListTransazioneToDTO(model);
				transazioneDTO.setData(modelDTO);
				return transazioneDTO;

			} else {
				System.out.println(
						"Errore presente nella riga 236 nella classe TransazioneController nel metodo findTransazioneByUtenteId");
				throw new Exception("L'utente non ha ancora effettuato nessuna transazione");
			}

		} catch (SQLException s) {
			s.printStackTrace();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);

		} finally {

			factory.closeSession();
		}
	}
	
	private void updateSaldoContoCorrente(Transazione transazione) throws Exception {
	    ContoCorrente conto = transazione.getContoCorrente();
	    ContoCorrente contoBeneficiario = transazione.getContoCorrenteBeneficiario();

	    if (transazione.getStatoTransazione().getId() == Costanti.STATO_TRANSAZIONE_APPROVATA) {
	        ContoCorrenteMapper mapperConto = SqlMapFactory.instance().getMapper(ContoCorrenteMapper.class);
	        double importo = transazione.getImporto();

	        if (transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_DEPOSITO) {
	            conto.setSaldo(conto.getSaldo() + importo);
	            
	        } else if (transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_PRELIEVO 
	        		|| transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_RICARICA 
	        		|| transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_BOLLETTINO) {
	            conto.setSaldo(conto.getSaldo() - importo);
	            
	        } else if (transazione.getTipoTransazione().getId() == Costanti.TIPO_TRANSAZIONE_BONIFICO) {
	            conto.setSaldo(conto.getSaldo() - importo);
	            contoBeneficiario.setSaldo(contoBeneficiario.getSaldo() + importo);
	        }
	        
	        crudConto.updateContoCorrente(mapperConto, conto);
	        
	        if (null != contoBeneficiario) {
	            crudConto.updateContoCorrente(mapperConto, contoBeneficiario);
	        }
	    }
	}
	
	

}
