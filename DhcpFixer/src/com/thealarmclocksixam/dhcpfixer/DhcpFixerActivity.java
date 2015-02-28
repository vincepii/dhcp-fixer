package com.thealarmclocksixam.dhcpfixer;

import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.exceptions.RootDeniedException;
import com.stericson.RootTools.execution.CommandCapture;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class DhcpFixerActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final Button fixButton = (Button) findViewById(R.id.fixButton);
        
        fixButton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View v) {
                fixDhcpProblem();
            }
        });
    }
    
    private void fixDhcpProblem() {
        if (RootTools.isAccessGiven()) {
            CommandCapture command = new CommandCapture(0, "cd /data/misc/dhcp", "rm dhcpcd-wlan0.lease dhcpcd-wlan0.pid");
            try {
                RootTools.getShell(true).add(command);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            } catch (RootDeniedException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Command executed, output is unknown", 
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Cannot get super user privilegies. Maybe your device is not rooted.", 
                    Toast.LENGTH_SHORT).show();
        }
    }
}