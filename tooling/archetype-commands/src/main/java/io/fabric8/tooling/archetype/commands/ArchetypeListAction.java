/**
 *  Copyright 2005-2014 Red Hat, Inc.
 *
 *  Red Hat licenses this file to you under the Apache License, version
 *  2.0 (the "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 *  implied.  See the License for the specific language governing
 *  permissions and limitations under the License.
 */
package io.fabric8.tooling.archetype.commands;

import io.fabric8.tooling.archetype.ArchetypeService;
import io.fabric8.tooling.archetype.catalog.Archetype;
import org.apache.felix.gogo.commands.Command;
import org.apache.felix.gogo.commands.Option;
import org.apache.karaf.shell.console.AbstractAction;

@Command(name = ArchetypeList.FUNCTION_VALUE, scope = ArchetypeList.SCOPE_VALUE, description = ArchetypeList.DESCRIPTION)
public class ArchetypeListAction extends AbstractAction {

    static final String FORMAT = "%-50s %s";
    static final String[] HEADERS = {"[artifactId]", "[description]"};

    static final String VERBOSE_FORMAT = "%-30s %-50s %-20s %s";
    static final String[] VERBOSE_HEADERS = {"[groupId]", "[artifactId]", "[version]", "[description]"};

    @Option(name = "-v", aliases = "--verbose", description = "Flag for verbose output", multiValued = false, required = false)
    private boolean verbose;

    private final ArchetypeService archetypeService;

    public ArchetypeListAction(ArchetypeService archetypeService) {
        this.archetypeService = archetypeService;
    }

    @Override
    protected Object doExecute() throws Exception {
        if (verbose) {
            System.out.println(String.format(VERBOSE_FORMAT, (Object[]) VERBOSE_HEADERS));
        } else {
            System.out.println(String.format(FORMAT, (Object[]) HEADERS));
        }

        for (Archetype archetype : archetypeService.listArchetypes()) {
            String nextLine;
            if (verbose) {
                nextLine = String.format(VERBOSE_FORMAT, archetype.groupId, archetype.artifactId, archetype.version, archetype.description);
            } else {
                // only list artifact id in short format
                nextLine = String.format(FORMAT, archetype.artifactId, archetype.description);
            }
            System.out.println(nextLine);
        }

        return null;
    }

}
