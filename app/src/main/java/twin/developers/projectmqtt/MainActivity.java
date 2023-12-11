package twin.developers.projectmqtt;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Mqtt mqttManager;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttManager = new Mqtt(getApplicationContext());
        mqttManager.connectToMqttBroker();

        btnEnviar = findViewById(R.id.buttonEnviarHora);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarTimePickerDialog();
            }
        });
    }

    private void mostrarTimePickerDialog() {
        final Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minutos = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String horaFormateada = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                        // Envía la hora a MQTT
                        mqttManager.publishMessage(horaFormateada);
                    }
                },
                hora,
                minutos,
                true // true si quieres el formato de 24 horas, false si prefieres el formato de 12 horas
        );
        timePickerDialog.show();
    }
}
