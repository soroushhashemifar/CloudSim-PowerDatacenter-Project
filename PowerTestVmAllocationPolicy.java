/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cloudsub.cloudsim.power_examples;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicy;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHostUtilizationHistory;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicyAbstract;
import org.cloudbus.cloudsim.power.PowerVmAllocationPolicyMigrationAbstract;

/**
 *
 * @author Zahra Bagheri
 */
public class PowerTestVmAllocationPolicy extends PowerVmAllocationPolicyAbstract{
    
    public PowerTestVmAllocationPolicy (List<? extends Host> hostList) {
		super(hostList);
    }
    
	/*
	 * (non-Javadoc)
	 * @see org.cloudbus.cloudsim.VmAllocationPolicy#optimizeAllocation(java.util.List)
	 */
	@Override
	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
        List<Map<String, Object>> migrationMap = new LinkedList<Map<String, Object>>();
        Map<String, Object> migrate = new HashMap<String, Object>();

        //Find VMs to migrate
        Vm vm = null;
        for (PowerHostUtilizationHistory host : this.<PowerHostUtilizationHistory> getHostList()) {
                if (host.getUtilizationOfCpu() * 100 > 95){
                        vm = host.getVmList().get(0);
                }
        }

        //Find target hosts
        Host allocatedHost = null;
        for (PowerHostUtilizationHistory host1 : this.<PowerHostUtilizationHistory> getHostList()){
                        if (host1.getUtilizationOfCpu() * 100 < 65){
                            allocatedHost = host1;
                        }
        }

        if (vm != null && allocatedHost != null){
                migrate.put("vm", vm);
                migrate.put("host", allocatedHost);
                migrationMap.add(migrate);
        }

        return migrationMap;
	}
}