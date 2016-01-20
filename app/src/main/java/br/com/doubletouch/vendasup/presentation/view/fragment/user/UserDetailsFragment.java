package br.com.doubletouch.vendasup.presentation.view.fragment.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.data.executor.JobExecutor;
import br.com.doubletouch.vendasup.domain.executor.PostExecutionThread;
import br.com.doubletouch.vendasup.domain.executor.ThreadExecutor;
import br.com.doubletouch.vendasup.domain.interactor.user.SaveUserUseCase;
import br.com.doubletouch.vendasup.domain.interactor.user.SaveUserUseCaseImpl;
import br.com.doubletouch.vendasup.presentation.UIThread;
import br.com.doubletouch.vendasup.presentation.navigation.Navigator;
import br.com.doubletouch.vendasup.presentation.presenter.UserDetailsPresenter;
import br.com.doubletouch.vendasup.presentation.view.UserDetailsView;
import br.com.doubletouch.vendasup.presentation.view.ViewHelper;
import br.com.doubletouch.vendasup.presentation.view.activity.BaseParallacxFragment;
import br.com.doubletouch.vendasup.presentation.view.activity.UserDetailsActivity;
import br.com.doubletouch.vendasup.presentation.view.components.PagerSlidingTabStrip;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolder;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Created by LADAIR on 15/02/2015.
 */
public class UserDetailsFragment extends BaseParallacxFragment implements UserDetailsView {

    private final int PAGES_NUMER = 2;

    private static final String ARGUMENT_VIEW_MODE = "kratos.INTENT_EXTRA_PARAM_EDITION";

    private UserDetailsPresenter userDetailsPresenter;

    @Optional
    @InjectView( R.id.iv_header )
    public ImageView iv_header;

    @Optional
    @InjectView( R.id.fl_header )
    public View fl_header;

    @InjectView( R.id.vp_header )
    public ViewPager vp_header;

    private PageAdaper pageAdaper;

    @InjectView( R.id.rl_progress )
    RelativeLayout rl_progress;

    private ImageLoader imageLoader;

    @InjectView( R.id.tabs )
    public PagerSlidingTabStrip mPagerSlidingTabStrip;

    private Activity activity;

    MenuItem menuEdit;

    private Navigator navigator;

    public UserDetailsFragment() {
        super();
    }

    private ViewMode viewMode;

    public static UserDetailsFragment newInstance( ViewMode viewMode ) {
        UserDetailsFragment productDetailsFragment = new UserDetailsFragment();
        Bundle argumentsBundle = new Bundle();
        argumentsBundle.putSerializable( ARGUMENT_VIEW_MODE, viewMode );
        productDetailsFragment.setArguments( argumentsBundle );
        return productDetailsFragment;
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        this.viewMode = ( ViewMode ) getArguments().getSerializable( ARGUMENT_VIEW_MODE );

        if ( viewMode == null ) {
            viewMode = ViewMode.VISUALIZACAO;
        }
        imageLoader = new ImageLoader( getContext() );
        navigator = new Navigator();

    }


    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        this.activity = activity;

    }


    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ) {
        View fragmentView;

        if ( ViewMode.EDICAO.equals( viewMode ) || ViewMode.INCLUSAO.equals( viewMode ) ) {
            fragmentView = inflater.inflate( R.layout.fragment_customer_details_form, container, false );
        } else {
            fragmentView = inflater.inflate( R.layout.fragment_customer_details, container, false );
        }

        ButterKnife.inject( this, fragmentView );


        return fragmentView;
    }


    @Override
    public void onActivityCreated( Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        switch ( viewMode ) {
            case EDICAO:
                this.render( VendasUp.getUser() );
                //this.userDetailsPresenter.initialize();
                break;
            case VISUALIZACAO:
                this.render( VendasUp.getUser() );
                //this.userDetailsPresenter.initialize();
                break;
            default:
                break;
        }

        setHasOptionsMenu( true );
    }


    @Override
    public void onCreateOptionsMenu( Menu menu, MenuInflater inflater ) {
        super.onCreateOptionsMenu( menu, inflater );

        inflater.inflate( R.menu.menu_details_view, menu );

        menu.findItem( R.id.it_delete ).setVisible( false );

        if ( ViewMode.EDICAO.equals( viewMode ) ) {
            menu.findItem( R.id.it_edit ).setVisible( false );
            menuEdit = menu.findItem( R.id.it_edit ).setVisible( false );
            menu.findItem( R.id.it_done ).setVisible( true );
        } else {
            menu.findItem( R.id.it_edit ).setVisible( true );
            menuEdit = menu.findItem( R.id.it_edit ).setVisible( true );
            menu.findItem( R.id.it_done ).setVisible( false );
        }
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch ( item.getItemId() ) {
            case android.R.id.home:
                navigator.previousActivity( activity );
                break;
            case R.id.it_edit:
                Intent it = UserDetailsActivity.getCallingIntent( activity, ViewMode.EDICAO );
                startActivityForResult( it, 1 );
                navigator.transitionGo( activity );
                break;
            case R.id.it_done:
                saveUser();
                break;
            default:
                super.onOptionsItemSelected( item );
        }
        return super.onOptionsItemSelected( item );
    }

    private void saveUser() {

        //Notifica todas os fragments filhos para valorizar os atributos com os dados alterados.
        SparseArrayCompat< ScrollTabHolder > scrollTabHolders = pageAdaper.getScrollTabHolders();

        for ( int i = 0; i < scrollTabHolders.size(); i++ ) {
            ScrollTabHolder scrollTabHolder = scrollTabHolders.get( i );
            scrollTabHolder.setAtributes( VendasUp.getUser() );
        }

        userDetailsPresenter.saveUser( VendasUp.getUser() );
    }


    @Override
    public void initializePresenter() {

        ThreadExecutor threadExecutor = JobExecutor.getInstance();
        PostExecutionThread postExecutionThread = UIThread.getInstance();

        SaveUserUseCase saveUserUseCase = new SaveUserUseCaseImpl( threadExecutor, postExecutionThread );

        userDetailsPresenter = new UserDetailsPresenter( this, saveUserUseCase );

    }


    @Override
    public void render( User user ) {

        if ( ViewMode.VISUALIZACAO.equals( viewMode ) ) {
            imageLoader.displayImage( user.getPictureUrl(), this.iv_header, R.drawable.jorge );
        }


        vp_header.setOffscreenPageLimit( PAGES_NUMER );
        pageAdaper = new PageAdaper( getFragmentManager(), user );
        pageAdaper.setTabHolderScrollingContent( new OnScrollTabHolderListner() );

        vp_header.setAdapter( pageAdaper );

        mPagerSlidingTabStrip.setViewPager( vp_header );
        mPagerSlidingTabStrip.setOnPageChangeListener( new OnPageChangeListener() );
        pageAdaper.notifyDataSetChanged();


    }

    @Override
    public void saved( User user ) {
        Toast.makeText( activity, "Perfil salvo com sucesso", Toast.LENGTH_LONG ).show();
        navigator.previousActivity( activity );
    }


    @Override
    public void showLoading() {
        this.rl_progress.setVisibility( View.VISIBLE );
        this.getActivity().setProgressBarIndeterminateVisibility( true );
    }

    @Override
    public void hideLoading() {
        this.rl_progress.setVisibility( View.GONE );
        this.getActivity().setProgressBarIndeterminateVisibility( false );
    }


    @Override
    public void showError( String message ) {
        this.showToastMessage( message );
    }

    @Override
    public Context getContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    /**
     * Page adapter que ira manipular as fragments das tabs
     */
    private class PageAdaper extends FragmentStatePagerAdapter {

        private User user;
        private SparseArrayCompat< ScrollTabHolder > scrollTabHolder;
        private ScrollTabHolder listener;

        private final String[] TITLES = { "Principal" };

        public void setTabHolderScrollingContent( ScrollTabHolder listener ) {
            this.listener = listener;
        }

        private PageAdaper( FragmentManager fm, User user ) {
            super( fm );
            this.scrollTabHolder = new SparseArrayCompat<>();
            this.user = user;
        }

        @Override
        public int getItemPosition( Object object ) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem( int position ) {
            ScrollTabHolderFragment fragment = null;

            switch ( position ) {
                case 0:
                    fragment = ( ScrollTabHolderFragment ) UserDetailsMainFragment.newInstance( position, viewMode );
                    break;
                default:
            }

            scrollTabHolder.put( position, fragment );

            if ( listener != null ) {
                fragment.setScrollTabHolder( listener );
            }
            return fragment;
        }


        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle( int position ) {
            return TITLES[ position ];
        }

        public SparseArrayCompat< ScrollTabHolder > getScrollTabHolders() {
            return scrollTabHolder;
        }
    }

    public class OnScrollTabHolderListner implements ScrollTabHolder {
        @Override
        public void adjustScroll( int scrollHeight ) {

        }

        @Override
        public void onScroll( AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount, int pagePosition ) {
            if ( ViewMode.VISUALIZACAO.equals( viewMode ) && vp_header.getCurrentItem() == pagePosition ) {
                int scrollY = getScrollY( view );
                ViewHelper.setTranslationY( fl_header, Math.max( -scrollY, mMinHeaderTranslation ) );
            }
        }

        @Override
        public void setAtributes( Object object ) {

        }

        @Override
        public void tabSelected() {

        }
    }

    /**
     * Listner para as tabs
     */
    public class OnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled( int position, float positionOffset, int positionOffsetPixels ) {

        }

        @Override
        public void onPageSelected( int position ) {

            SparseArrayCompat< ScrollTabHolder > scrollTabHolders = pageAdaper.getScrollTabHolders();
            ScrollTabHolder currentHolder = scrollTabHolders.valueAt( position );

            if ( ViewMode.VISUALIZACAO.equals( viewMode ) ) {
                currentHolder.adjustScroll( ( int ) ( fl_header.getHeight() + ViewHelper.getTranslationY( fl_header ) ) );
            }


        }

        @Override
        public void onPageScrollStateChanged( int state ) {

        }

    }


    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );

        this.render( VendasUp.getUser() );
    }

}
