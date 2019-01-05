package com.minebarteksa.orion.integrations;

import com.minebarteksa.orion.integrations.infoprovider.IPData;
import java.util.List;

public interface IOrionInfoProvider
{
    List<String> addInfo(IPData data); // ToDo - Better information gathering system
}
