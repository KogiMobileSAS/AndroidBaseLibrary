package com.kogimobile.android.baselibrary.rest;

import com.kogimobile.android.baselibrary.rest.model.ServiceConfiguration;

import java.util.Collection;
import java.util.Hashtable;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * @author kogiandroid on 1/14/16.
 * Modified by Julian Cardona 04/19/16.
 */
public class RestClientManager<T> {

    private Hashtable<Class, BaseService> apiServicesInstances= new Hashtable<>();

    public RestClientManager(ServiceConfiguration... serviceConfigurations){
        setServiceConfigurations(serviceConfigurations);
    }

    public RestClientManager addServiceConfiguration(ServiceConfiguration serviceConfiguration){
        initializeServiceConfiguration(serviceConfiguration);
        return this;
    }

    public T getService(Class<T> baseServiceClass){
        return baseServiceClass.cast(apiServicesInstances.get(baseServiceClass));
    }

    public Collection<BaseService> getServices(){
        return apiServicesInstances.values();
    }

    public RestClientManager deleteService(Class serviceConfigurationClass){
        apiServicesInstances.remove(serviceConfigurationClass);
        return this;
    }


    public RestClientManager deleteServiceConfigurations(){
        apiServicesInstances = new Hashtable<>();
        return this;
    }

    private void setServiceConfigurations(ServiceConfiguration[] serviceConfigurations) {
        apiServicesInstances = new Hashtable<>();
        for (ServiceConfiguration currentServiceConfiguration : serviceConfigurations){
            initializeServiceConfiguration(currentServiceConfiguration);
        }
    }

    private void initializeServiceConfiguration(final ServiceConfiguration currentServiceConfiguration) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(currentServiceConfiguration.getBaseURL())
                .addConverterFactory(currentServiceConfiguration.getConverter())
                .client(currentServiceConfiguration.getClient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        Object apiServiceInterface = retrofit.create(currentServiceConfiguration.getInterfaceClass());
        try {
            BaseService apiService = (BaseService) currentServiceConfiguration.getApiServiceFactory().factory();
            apiService.setApiServiceInterface(apiServiceInterface);
            apiServicesInstances.put(currentServiceConfiguration.getApiServiceClass(), apiService);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
