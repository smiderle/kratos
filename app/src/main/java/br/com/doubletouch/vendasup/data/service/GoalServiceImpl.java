package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.database.dao.CustomerDAO;
import br.com.doubletouch.vendasup.data.database.dao.GoalDAO;
import br.com.doubletouch.vendasup.data.entity.Customer;
import br.com.doubletouch.vendasup.data.entity.Goal;
import br.com.doubletouch.vendasup.data.exception.CustomerNotFoundException;
import br.com.doubletouch.vendasup.data.exception.RepositoryErrorBundle;

/**
 * Created by LADAIR on 18/02/2015.
 */
public class GoalServiceImpl implements GoalService {

    private GoalDAO goalDAO;

    @Override
    public void saveOrUpdateSynchronous(List<Goal> goals) {
        goalDAO = new GoalDAO();
        goalDAO.insert(goals);
    }
}
