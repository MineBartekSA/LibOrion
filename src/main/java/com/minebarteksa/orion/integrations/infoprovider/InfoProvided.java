package com.minebarteksa.orion.integrations.infoprovider;

import com.minebarteksa.orion.integrations.infoprovider.entry.IInfoEntry;
import com.minebarteksa.orion.integrations.infoprovider.entry.ProgressEntry;
import com.minebarteksa.orion.integrations.infoprovider.entry.TextEntry;

import java.util.ArrayList;
import java.util.List;

public class InfoProvided // TODO: Finish
{
    private List<IInfoEntry> entries = new ArrayList<>();

    public void addEntry(IInfoEntry e) { entries.add(e); }

    public void addText(String text) { entries.add(new TextEntry(text)); }

    public void addProgress(int progress) { entries.add(new ProgressEntry(progress)); }
}
