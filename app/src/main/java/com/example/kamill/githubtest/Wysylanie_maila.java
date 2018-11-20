package com.example.kamill.githubtest;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.Properties;

public class Wysylanie_maila extends AppCompatActivity {

    final String emailPort = "587";// gmail's smtp port
    final String smtpAuth = "true";
    final String starttls = "true";
    final String emailHost = "smtp.gmail.com";

    String fromEmail;
    String fromPassword;
    List toEmailList;
    String emailSubject;
    String emailBody;

    Properties emailProperties;
    PackageInstaller.Session mailSession;
    MimeMessage emailMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wysylanie_maila);
    }



}
