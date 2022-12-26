/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blacklistvalidator;

import edu.eci.arsw.spamkeywordsdatasource.HostBlacklistsDataSourceFacade;
import edu.eci.arsw.threads.ServidorThread;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author hcadavid
 */
public class HostBlackListsValidator {

    private static final int BLACK_LIST_ALARM_COUNT=5;
    
    /**
     * Check the given host's IP address in all the available black lists,
     * and report it as NOT Trustworthy when such IP was reported in at least
     * BLACK_LIST_ALARM_COUNT lists, or as Trustworthy in any other case.
     * The search is not exhaustive: When the number of occurrences is equal to
     * BLACK_LIST_ALARM_COUNT, the search is finished, the host reported as
     * NOT Trustworthy, and the list of the five blacklists returned.
     * @param ipaddress suspicious host's IP address.
     * @return  Blacklists numbers where the given host's IP address was found.
     * @throws InterruptedException 
     */
    public List<Integer> checkHost(String ipaddress, int N) throws InterruptedException{
        List<Thread> lista = new ArrayList<Thread>();
        LinkedList<Integer> blackListOcurrences = new LinkedList<>();
        HostBlacklistsDataSourceFacade skds=HostBlacklistsDataSourceFacade.getInstance();
        int i, ocurrencesCount=0;
        int checkedListsCount=0;

        int div = skds.getRegisteredServersCount() / N;

        for (i=1;i<=N;i++) {
            Thread e =  new ServidorThread((i-1)*(div)+1,i*(div), skds, ipaddress);
            lista.add(e);
            e.start();
        }

        for (i=0;i<N;i++) {
            lista.get(i).join();
        }

        for (i=0;i<N;i++) {
            ServidorThread r =  (ServidorThread) lista.get(i);
            ocurrencesCount += r.numeroOcurrencias();
            checkedListsCount += r.numeroCheck();
            LinkedList<Integer> blackList = r.listaNegra();

            for(int j=0;j<blackList.size();j++) {
                int num = blackList.get(j);
                blackListOcurrences.add(num);
            }

        }

        if (ocurrencesCount>=BLACK_LIST_ALARM_COUNT){
            skds.reportAsNotTrustworthy(ipaddress);
        }
        else{
            skds.reportAsTrustworthy(ipaddress);
        }                
        
        LOG.log(Level.INFO, "Checked Black Lists:{0} of {1}", new Object[]{checkedListsCount, skds.getRegisteredServersCount()});
        
        return blackListOcurrences;
    }
    
    
    private static final Logger LOG = Logger.getLogger(HostBlackListsValidator.class.getName());
    
    
    
}
