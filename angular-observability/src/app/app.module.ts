import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon'
import { MatButtonModule } from '@angular/material/button'
import { MatInputModule } from '@angular/material/input'
import { HttpClientModule } from '@angular/common/http';
import { OpenTelemetryInterceptorModule, OtelColExporterModule, CompositePropagatorModule } from '@jufab/opentelemetry-angular-interceptor';
import { DiagLogLevel } from '@opentelemetry/api'


@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    MatDividerModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    HttpClientModule,
    OtelColExporterModule,
    CompositePropagatorModule,
    OpenTelemetryInterceptorModule.forRoot({
      commonConfig: {
        console: true, //(boolean) Display trace on console
        production: false, //(boolean) Send trace with BatchSpanProcessor (true) or SimpleSpanProcessor (false)
        logBody: true, //(boolean) true add body in a log, nothing otherwise
        serviceName: 'angular-observability', //Service name send in trace
        probabilitySampler: '0.7', //Samples a configurable percentage of traces, string value between '0' to '1'
        logLevel: DiagLogLevel.ALL //(Enum) DiagLogLevel is an Enum from @opentelemetry/api
      },
      batchSpanProcessorConfig: { //Only if production = true in commonConfig
        maxQueueSize: '2048', // The maximum queue size. After the size is reached spans are dropped.
        maxExportBatchSize: '512', // The maximum batch size of every export. It must be smaller or equal to maxQueueSize.
        scheduledDelayMillis: '5000', // The interval between two consecutive exports
        exportTimeoutMillis: '30000', // How long the export can run before it is cancelled
      },
      otelcolConfig: {
        url: 'http://localhost:55681/v1/traces', //URL of opentelemetry collector
        headers: {'Content-Type': 'application/json'}
      },
      // jaegerPropagatorConfig: {
      //   customHeader: 'custom-header',
      // }
    })
  ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {

}
