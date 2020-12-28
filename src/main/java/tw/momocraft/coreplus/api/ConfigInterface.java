package tw.momocraft.coreplus.api;

import java.util.List;

public interface ConfigInterface {

    /**
     * Adding custom group to the list and checking the type is exist.
     *
     * @param prefix the executing plugin prefix.
     * @param list the checking list.
     * @param type the type of the list like Entity or Material.
     * @return a new list without custom group.
     */
    List<String> getTypeList(String prefix, List<String> list, String type);

}
