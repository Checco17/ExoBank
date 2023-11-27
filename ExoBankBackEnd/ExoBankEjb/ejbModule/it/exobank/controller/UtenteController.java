package it.exobank.controller;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import it.exobank.DTO.*;
import it.exobank.crud.UtenteCrud;
import it.exobank.ejbInterface.UtenteControllerInterface;
import it.exobank.mapper.UtenteMapper;
import it.exobank.model.Utente;
import it.exobank.sqlmapfactory.SqlMapFactory;
import it.exobank.methods.Convertitore;
import it.exobank.utils.Costanti;
import it.exobank.email.EmailConst;
import it.exobank.email.SendEmail;
import it.exobank.methods.Validatore;

/*
 * INSERISCO OPERAZIONE DI COMMIT E ROLLBACK NONOSTANTE NON SIANO NECESSARIE 
 * POICHE' LE TRANSAZIONE VENGONO GESTITE DAL CONTAINER DI WILD FLY 
 * IN QUANTO HO IMPOSTATO IL TRANSACTIONMANAGER TYPE= MANAGED ANZICHE' JDBC
 */

@Stateless(name = "UtenteControllerInterface")
@LocalBean
public class UtenteController implements UtenteControllerInterface {

	private UtenteCrud crud = UtenteCrud.getInstance();

	public UtenteController() {

	}

	@Override
	public Dto<List<Utente>> findAllUtente() throws SQLException, Exception {
		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<List<Utente>> dto = new Dto<List<Utente>>();
		try {
			UtenteMapper mapper = factory.getMapper(UtenteMapper.class);
			List<Utente> list = crud.findAllUtente(mapper);

			if (null != list) {
				List<Utente> listDTO = new Convertitore().fromListUtenteToDTO(list);
				dto.setData(listDTO);
				return dto;

			} else {
				System.out
						.println("Errore presente nella riga 36 nella classeUtenteController nel metodo findAllUtente");
				throw new Exception("Non sono presenti utenti registrati");

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
	public Dto<Utente> insertUtente(Utente utenteRegister) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<Utente> utenteDTO = new Dto<Utente>();
		try {
			UtenteMapper mapper = factory.getMapper(UtenteMapper.class);

			if (new Validatore().validatoreUtente(utenteRegister)) {

				Utente model = crud.insertUtente(mapper, utenteRegister);

				if (null != model) {
					Utente modelDTO = new Convertitore().fromUtenteToDTO(model);
					utenteDTO.setData(modelDTO);					
					new SendEmail().sendEmail(Costanti.EMAIL_DESTINATARIO, EmailConst.BENVENUTO_IN_EXOBANK, EmailConst.GET_MESSAGE_BENVENUTO(utenteRegister), null, null);
					factory.commitSession();
					return utenteDTO;
				} else {
					factory.rollbackSession();
					System.out.println(
							"Errore presente nella riga 75 nella classeUtenteController nel metodo insertUtente");
					throw new Exception("La registrazione non è andata a buon fine");

				}
			} else {
				System.out
						.println("Errore presente nella riga 73 nella classeUtenteController nel metodo insertUtente");
				throw new Exception("Errore di validazione dell'utente");
			}

		} catch (SQLException s) {
			s.printStackTrace();
			factory.rollbackSession();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			factory.rollbackSession();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);

		} finally {

			factory.closeSession();
		}

	}

	@Override
	public Dto<Utente> findUtenteById(Integer utenteId) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<Utente> utenteDTO = new Dto<Utente>();
		try {
			UtenteMapper mapper = factory.getMapper(UtenteMapper.class);
			Utente model = crud.findUtenteById(mapper, utenteId);

			if (null != model) {
				Utente modelDTO = new Convertitore().fromUtenteToDTO(model);
				utenteDTO.setData(modelDTO);
				return utenteDTO;

			} else {
				System.out.println(
						"Errore presente nella riga 120 nella classeUtenteController nel metodo findUtenteById");
				throw new Exception("Non è presente nessun utente associato a questo identificativo");
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
	public Dto<Utente> findByEmailPassword(Utente utenteLogin) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<Utente> utenteDTO = new Dto<Utente>();
		try {
			UtenteMapper mapper = factory.getMapper(UtenteMapper.class);
			Utente model = crud.findByEmailPassword(mapper, utenteLogin);

			if (null != model) {
				Utente modelDTO = new Convertitore().fromUtenteToDTO(model);
				utenteDTO.setData(modelDTO);
				return utenteDTO;

			} else {
				System.out.println(
						"Errore presente nella riga 154 nella classe UtenteController nel metodo findByEmailPassword ");
				throw new Exception("Credenziali d'accesso errate");

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

}
