/**
 * Copyright (c) 2015-2016 Peti Koch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.petikoch.examples.mvvm_rxjava.example7a;

import ch.petikoch.examples.mvvm_rxjava.datatypes.LogRow;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import rx.Observable;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

class Example_7a_Model {

    public Observable<LogRow> getLogs() {
        return Observable.interval(1, TimeUnit.SECONDS)
                .map(incrementingNumber -> new LogRow(
                        DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()),
                        "Status " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 5)),
                        "Action " + incrementingNumber + " from " + Thread.currentThread().getName()))
                .map(logRow -> {
                    if (ThreadLocalRandom.current().nextInt(5) == 0) {
                        // occasionally happens something unexpected...
                        throw new RuntimeException("This is totally unexpected...");
                    } else {
                        return logRow;
                    }
                })
                .doOnNext(text -> SysOutUtils.sysout("Sending: " + text));
    }

}
