package it.exobank.controller;

import it.exobank.crud.ContoCorrenteCrud;
import it.exobank.ejbInterface.ContoCorrenteControllerInterface;
import it.exobank.mapper.ContoCorrenteMapper;
import it.exobank.DTO.*;
import it.exobank.model.ContoCorrente;
import it.exobank.sqlmapfactory.SqlMapFactory;
import it.exobank.methods.Convertitore;
import it.exobank.utils.Costanti;
import it.exobank.email.EmailConst;
import it.exobank.email.SendEmail;
import it.exobank.methods.Validatore;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/*
 * INSERISCO OPERAZIONE DI COMMIT E ROLLBACK NONOSTANTE NON SIANO NECESSARIE 
 * POICHE' LE TRANSAZIONE VENGONO GESTITE DAL CONTAINER DI WILD FLY 
 * IN QUANTO HO IMPOSTATO IL TRANSACTIONMANAGER TYPE= MANAGED ANZICHE' JDBC
 */

@Stateless(name = "ContoCorrenteControllerInterface")
@LocalBean
public class ContoCorrenteController implements ContoCorrenteControllerInterface {

	private ContoCorrenteCrud crud = ContoCorrenteCrud.getInstance();

	public ContoCorrenteController() {

	}

	@Override
	public Dto<ContoCorrente> insertContoCorrente(ContoCorrente contoCorrente) throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<ContoCorrente> contoDTO = new Dto<ContoCorrente>();
		try {
			ContoCorrenteMapper mapper = factory.getMapper(ContoCorrenteMapper.class);

			if (new Validatore().validatoreContoCorrente(contoCorrente)) {

				ContoCorrente model = crud.insertContoCorrente(mapper, contoCorrente);

				if (null != model) {
					ContoCorrente modelDTO = new Convertitore().fromContoCorrenteToDTO(model);
					contoDTO.setData(modelDTO);
					new SendEmail().sendEmail(Costanti.EMAIL_DESTINATARIO, EmailConst.APERTURA_CONTO, EmailConst.GET_MESSAGE_APERTURA_CONTO(contoCorrente.getUtente()), null, null);
					factory.commitSession();
					return contoDTO;

				} else {
					factory.rollbackSession();
					System.out.println(
							"Errore presente nella riga 40 nella classe ContoCorrenteController nel metodo insertContoCorrente");
					throw new Exception("Errore inserimento conto corrente");
				}

			} else {
				System.out.println(
						"Errore presente nella riga 38 nella classe ContoCorrenteController nel metodo insertContoCorrente");
				throw new Exception("Errore di validazione del conto corrente");
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
	public Dto<List<ContoCorrente>> findAllContoCorrente() throws SQLException, Exception {
		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<List<ContoCorrente>> dto = new Dto<List<ContoCorrente>>();
		try {
			ContoCorrenteMapper mapper = factory.getMapper(ContoCorrenteMapper.class);
			List<ContoCorrente> conti = crud.findAllContoCorrente(mapper);

			if (null != conti) {
				List<ContoCorrente> contiDTO = new Convertitore().fromListContoCorrenteToDTO(conti);
				dto.setData(contiDTO);
				return dto;

			} else {
				System.out.println(
						"Errore presente nella riga 82 nella classe ContoCorrenteController nel metodo findAllContoCorrente");
				throw new Exception("Non sono presenti conti correnti registrati");
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
	public Dto<ContoCorrente> findContoCorrenteByUtenteId(Integer i) throws SQLException, Exception {
		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<ContoCorrente> contoDTO = new Dto<ContoCorrente>();
		try {
			ContoCorrenteMapper mapper = factory.getMapper(ContoCorrenteMapper.class);
			ContoCorrente model = crud.findContoCorrenteByUtenteId(mapper, i);

			if (null != model) {
				ContoCorrente modelDTO = new Convertitore().fromContoCorrenteToDTO(model);
				contoDTO.setData(modelDTO);
				return contoDTO;

			} else {
				System.out.println(
						"Errore presente nella riga 117 nella classe ContoCorrenteController nel metodo findContoCorrenteByUtenteId");
				throw new Exception("Non possiedi un conto corrente associato");
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
	public Dto<ContoCorrente> updateContoCorrente(ContoCorrente contoCorrente) throws SQLException, Exception {
		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		Dto<ContoCorrente> contoDTO = new Dto<ContoCorrente>();
		try {
			ContoCorrenteMapper mapper = factory.getMapper(ContoCorrenteMapper.class);
			ContoCorrente model = crud.updateContoCorrente(mapper, contoCorrente);

			if (null != model) {
				ContoCorrente modelDTO = new Convertitore().fromContoCorrenteToDTO(model);
				contoDTO.setData(modelDTO);
				if (model.getStatoContoCorrente().getStatoContoCorrenteId() == Costanti.STATO_CONTO_CORRENTE_ATTIVO) {
					new SendEmail().sendEmail(Costanti.EMAIL_DESTINATARIO, EmailConst.APPROVAZIONE_CONTO, EmailConst.GET_MESSAGE_APPROVAZIONE_CONTO(contoCorrente.getUtente()), null, null);
				}
				factory.commitSession();
				return contoDTO;

			} else {
				factory.rollbackSession();
				System.out.println(
						"Errore presente nella riga 151 nella classe ContoCorrenteController nel metodo updateContoCorrente");
				throw new Exception("Non è stato possibile aggiornare il conto corrente");

			}

		} catch (SQLException s) {
			s.printStackTrace();
			factory.rollbackSession();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			factory.rollbackSession();
			throw new Exception((e.getMessage()) != null ? e.getMessage() : Costanti.CONTATTA_ASSISTENZA);

		}

	}

}
