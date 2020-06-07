package com.alekseyld.collegetimetable.rx.subscriber;

import com.alekseyld.collegetimetable.exception.UncriticalException;
import com.crashlytics.android.Crashlytics;
//import com.crashlytics.android.Crashlytics;

/**
 * Created by Alekseyld on 02.09.2016.
 */

public class BaseSubscriber<T> extends rx.Subscriber<T> {
    @Override public void onCompleted() {
        // no-op by default.
    }

    @Override public void onError(Throwable e) {
        if (!(e instanceof UncriticalException)) {
            Crashlytics.logException(e);
        }
    }

    @Override public void onNext(T t) {
        // no-op by default.
    }
}