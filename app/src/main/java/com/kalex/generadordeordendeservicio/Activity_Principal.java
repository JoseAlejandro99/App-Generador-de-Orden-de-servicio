package com.kalex.generadordeordendeservicio;

        import android.content.Intent;
        import android.os.Bundle;
        import android.util.Log;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import androidx.navigation.NavController;
        import androidx.navigation.Navigation;
        import androidx.navigation.ui.AppBarConfiguration;
        import androidx.navigation.ui.NavigationUI;

        import com.google.android.material.bottomnavigation.BottomNavigationView;
        import com.kalex.generadordeordendeservicio.databinding.ActivityPrincipalBinding;

public class Activity_Principal extends AppCompatActivity {

    private ActivityPrincipalBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar la ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Obtener el nivel de usuario desde el Intent
        int nivelUsuario = getIntent().getIntExtra("nivelUsuario", 0);
        Log.d("Activity_Principal", "Nivel de usuario: " + nivelUsuario);


        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Configurar opciones de menú según el nivel de usuario
        if (nivelUsuario == 1) {
            // Inflar el menú para el nivel de usuario 1
            navView.inflateMenu(R.menu.bottom_nav_menu_nivel1);
        } else if (nivelUsuario == 2) {
            // Inflar el menú para el nivel de usuario 2
            navView.inflateMenu(R.menu.bottom_nav_menu_nivel2);
        } else {
            Log.d("Activity_principal", "Error al verificar el nivel de usuario");
        }


        // Pasar cada ID de menú como un conjunto de IDs porque cada
        // menú debe considerarse como destinos de nivel superior.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_principal);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

}

