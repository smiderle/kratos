package br.com.doubletouch.vendasup.data.service;

import java.util.List;

import br.com.doubletouch.vendasup.data.entity.Goal;
import br.com.doubletouch.vendasup.data.entity.User;

/**
 *
 * Interface que representa o Repositório para obter dados relacionados ao  {@link br.com.doubletouch.vendasup.data.entity.User}
 *
 * Created by LADAIR on 18/02/2015.
 */
public interface GoalService {


    public void saveOrUpdateSynchronous(List<Goal> goals) ;
}
