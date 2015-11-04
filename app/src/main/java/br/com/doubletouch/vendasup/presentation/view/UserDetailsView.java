package br.com.doubletouch.vendasup.presentation.view;

import br.com.doubletouch.vendasup.data.entity.User;

/**
 *
 * Created by LADAIR on 15/02/2015.
 */
public interface UserDetailsView extends  LoadDataView {

    void render(User user);

    void saved(User user);
}
