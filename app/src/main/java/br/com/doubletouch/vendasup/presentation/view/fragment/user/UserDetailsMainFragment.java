package br.com.doubletouch.vendasup.presentation.view.fragment.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.io.InputStream;
import java.util.UUID;

import br.com.doubletouch.vendasup.R;
import br.com.doubletouch.vendasup.VendasUp;
import br.com.doubletouch.vendasup.data.entity.User;
import br.com.doubletouch.vendasup.data.entity.enumeration.ViewMode;
import br.com.doubletouch.vendasup.presentation.view.adapter.DetailsBaseAdapter;
import br.com.doubletouch.vendasup.presentation.view.components.parallax.ScrollTabHolderFragment;
import br.com.doubletouch.vendasup.util.image.ImageLoader;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.Optional;

/**
 * Created by LADAIR on 17/03/2015.
 */
public class UserDetailsMainFragment extends ScrollTabHolderFragment {


    /**
     * Um listview criado para agrupar o conteudo da tab.
     * Foi utilizado um listview, pois ele possui o evento AbsListView.OnScrollListener ,
     * necessario para functionar os efeitos parallax das tabs em baixo da imagem.
     */
    private ListView lv_content;

    private ScrollView sv_content;


    @InjectView( R.id.tv_user_email )
    @Optional
    TextView tv_user_email;


    @InjectView( R.id.tv_user_name )
    @Optional
    TextView tv_user_name;


    @InjectView( R.id.tv_user_password )
    @Optional
    TextView tv_user_password;

    @InjectView( R.id.et_user_name )
    @Optional
    EditText et_user_name;


    @InjectView( R.id.et_user_password )
    @Optional
    EditText et_user_password;


    @Optional
    @InjectView( R.id.iv_user_picture )
    public ImageView iv_user_picture;

    @Optional
    @InjectView( R.id.img_user_picture )
    public ImageView img_user_picture;

    @Optional
    @InjectView( R.id.bt_user_picture )
    public Button bt_user_picture;

    private ImageLoader imageLoader;

    private User user = VendasUp.getUser();


    public static final String ARGS = "position";
    public static final String ARGS_VIEW_MODE = "view_mode";
    private int position;

    private Activity activity;

    private ViewMode viewMode;

    private String nomeFotoAlterada = VendasUp.getUser().getPictureUrl();

    public static Fragment newInstance( int position, ViewMode viewMode ) {
        UserDetailsMainFragment fragment = new UserDetailsMainFragment();
        Bundle bundle = new Bundle();
        bundle.putInt( ARGS, position );
        bundle.putSerializable( ARGS_VIEW_MODE, viewMode );
        fragment.setArguments( bundle );
        return fragment;
    }


    @Override
    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        position = getArguments().getInt( ARGS );
        viewMode = ( ViewMode ) getArguments().getSerializable( ARGS_VIEW_MODE );
        imageLoader = new ImageLoader( getActivity().getApplicationContext() );
    }

    @Override
    public void onAttach( Activity activity ) {
        super.onAttach( activity );

        this.activity = activity;
    }


    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        if ( ViewMode.EDICAO.equals( viewMode ) || ViewMode.INCLUSAO.equals( viewMode ) ) {
            sv_content = new ScrollView( activity );
            sv_content.setLayoutParams( new AbsListView.LayoutParams( AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT ) );
            sv_content.setPadding( 0, ( int ) getResources().getDimension( R.dimen.parallax_tab_height ), 0, 0 );
            View viewCustomerDetails = inflater.inflate( R.layout.view_user_details_main, null );

            bind( viewCustomerDetails );

            sv_content.addView( viewCustomerDetails );
            return sv_content;
        } else {
            lv_content = new ListView( activity );
            lv_content.setLayoutParams( new AbsListView.LayoutParams( AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT ) );

            View placeHolderView = inflater.inflate( R.layout.component_parallax_header_placeholder, lv_content, false );

            lv_content.addHeaderView( placeHolderView );
            return lv_content;
        }
    }


    @Override
    public void onActivityCreated( @Nullable Bundle savedInstanceState ) {
        super.onActivityCreated( savedInstanceState );

        if ( ViewMode.EDICAO.equals( viewMode ) || ViewMode.INCLUSAO.equals( viewMode ) ) {


        } else {
            lv_content.setOnScrollListener( new OnScrollListner() );
            lv_content.setAdapter( new DetailsPersonalAdapter( activity ) );
        }

    }

    private void bind( View view ) {

        ButterKnife.inject( this, view );


        if ( ViewMode.EDICAO.equals( viewMode ) ) {

            ( ( ViewSwitcher ) view.findViewById( R.id.vs_user_name ) ).showNext();
            ( ( ViewSwitcher ) view.findViewById( R.id.vs_user_password ) ).showNext();

            this.tv_user_email.setText( user.getEmail() );
            this.et_user_name.setText( user.getName() );
            this.et_user_password.setText( user.getPassword() );
            this.iv_user_picture.setVisibility( View.VISIBLE );
            this.bt_user_picture.setVisibility( View.VISIBLE );
            this.img_user_picture.setVisibility( View.VISIBLE );

            imageLoader.displayImage( user.getPictureUrl(), this.iv_user_picture, R.drawable.jorge );

        } else {

            this.tv_user_email.setText( user.getEmail() );
            this.tv_user_name.setText( user.getName() );
            this.tv_user_password.setText( user.getPassword() );


        }
    }


    @Override
    public void adjustScroll( int scrollHeight ) {
        if ( lv_content == null || ( scrollHeight == 0 && lv_content.getFirstVisiblePosition() >= 1 ) ) {
            return;
        }

        lv_content.setSelectionFromTop( 1, scrollHeight );
    }


    @Override
    public void setAtributes( Object object ) {

        VendasUp.getUser().setPictureUrl( nomeFotoAlterada );
        VendasUp.getUser().setName( et_user_name.getText().toString() );
        VendasUp.getUser().setPassword( et_user_password.getText().toString() );


    }

    @Override
    public void tabSelected() {

    }


    private class OnScrollListner implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged( AbsListView view, int scrollState ) {

        }

        @Override
        public void onScroll( AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount ) {
            if ( mScrollTabHolder != null )
                mScrollTabHolder.onScroll( view, firstVisibleItem, visibleItemCount, totalItemCount, position );
        }
    }


    /**
     * Adapter para o listview(somente com uma linha) com os dados pessoais
     */
    public class DetailsPersonalAdapter extends DetailsBaseAdapter {

        private Context context;

        public DetailsPersonalAdapter( Context context ) {
            this.context = context;
        }

        @Override
        public View getView( int position, View convertView, ViewGroup parent ) {
            LayoutInflater inflater = ( LayoutInflater ) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            View view = inflater.inflate( R.layout.view_user_details_main, null );

            bind( view );

            return view;
        }

        //Desativa o evento onclick no item do listview.
        @Override
        public boolean isEnabled( int position ) {
            return false;
        }
    }

    @OnClick( R.id.bt_user_picture )
    public void onClickChangePicture() {

        Intent takePicture = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

        Intent pickIntent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
        pickIntent.setType( "image/*" );

        Intent chooserIntent = Intent.createChooser( takePicture, "Foto do Perfil" );

        chooserIntent.putExtra( Intent.EXTRA_INITIAL_INTENTS, new Intent[]{ pickIntent } );


        startActivityForResult( chooserIntent, 10 );

    }

    @Override
    public void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult( requestCode, resultCode, data );
        if ( requestCode == 10 && resultCode == Activity.RESULT_OK ) {
            if ( data == null ) {
                return;
            }
            try {

                if ( data.getData() == null ) {

                    Bitmap photo = ( Bitmap ) data.getExtras().get( "data" );

                    String nomeArquivoFinal = UUID.randomUUID().toString() + ".jpg";

                    imageLoader.add( photo, nomeArquivoFinal );

                    nomeFotoAlterada = nomeArquivoFinal;
                    imageLoader.displayImage( nomeArquivoFinal, this.iv_user_picture );


                } else {

                    InputStream inputStream = getActivity().getContentResolver().openInputStream( data.getData() );

                    String path = getFileName( data.getData() );

                    //Pega somente o nome do arquivo
                    path = path.substring( path.lastIndexOf( "/" ) + 1 );

                    String urlRandomica = UUID.randomUUID().toString();

                    String nomeArquivoFinal = urlRandomica.concat( path );

                    imageLoader.addBitmap( inputStream, nomeArquivoFinal );

                    nomeFotoAlterada = nomeArquivoFinal;

                    imageLoader.displayImage( nomeFotoAlterada, this.iv_user_picture );

                }

            } catch ( java.io.IOException e ) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName( Uri uri ) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings( "deprecation" )
        Cursor cursor = activity.managedQuery( uri, projection, null, null, null );
        int column_index = cursor
                .getColumnIndexOrThrow( MediaStore.Images.Media.DATA );
        cursor.moveToFirst();
        String path = cursor.getString( column_index );

        return path.substring( path.lastIndexOf( "/" ) + 1 );
    }

}
