package it.exobank.controller;

import it.exobank.crud.StatoContoCorrenteCrud;
import java.sql.SQLException;
import it.exobank.ejbInterface.StatoContoCorrenteControllerInterface;
import it.exobank.mapper.StatoContoCorrenteMapper;
import it.exobank.model.StatoContoCorrente;
import it.exobank.sqlmapfactory.SqlMapFactory;
import it.exobank.utils.Costanti;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless(name = "StatoContoCorrenteControllerInterface")
@LocalBean
public class StatoContoCorrenteController implements StatoContoCorrenteControllerInterface {

	private StatoContoCorrenteCrud crud = StatoContoCorrenteCrud.getInstance();

	public StatoContoCorrenteController() {

	}

	@Override
	public List<StatoContoCorrente> findAllStatoContoCorrente() throws SQLException, Exception {

		SqlMapFactory.instance().openSession();
		SqlMapFactory factory = SqlMapFactory.instance();
		List<StatoContoCorrente> stati = new ArrayList<StatoContoCorrente>();
		try {
			StatoContoCorrenteMapper mapper = factory.getMapper(StatoContoCorrenteMapper.class);
			stati = crud.findAllStatoContoCorrente(mapper);
			if (null != stati) {
				return stati;
			} else {

				throw new Exception("La ricerca non ha prodotto alcun risultato");
			}

		} catch (SQLException s) {
			s.printStackTrace();
			throw new SQLException(Costanti.CONTATTA_ASSISTENZA);

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(Costanti.CONTATTA_ASSISTENZA);

		} finally {
			factory.closeSession();
		}

	}

}
