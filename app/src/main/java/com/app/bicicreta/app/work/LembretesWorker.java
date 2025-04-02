package com.app.bicicreta.app.work;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.app.bicicreta.app.activity.MainActivity;
import com.app.bicicreta.app.service.NotificationLocalService;

public class LembretesWorker extends Worker {
    private Context _context;
    public LembretesWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
        _context = context;
    }
    @NonNull
    @Override
    public Result doWork() {
        Log.d("LembretesWorker", "Executando notificação de 15 minutos");
        notificar();
        return Result.success();
    }

    private void notificar() {
        NotificationLocalService notificacao = new NotificationLocalService(_context, MainActivity.class);
        notificacao.createNotification("Bicicreta", "Teste de 15 minutos");
    }
}
