package io.papermc.lib.environments;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VersionDetectionTest {

    @Test
    void testReleaseCandidate() {
        assertVersionIs("(MC: 1.18 Release Candidate 3)", 18, 0, -1, 3);
        assertVersionIs("(MC: 1.17.1 release candidate 1)", 17, 1, -1, 1);
    }

    @Test
    void testPreRelease() {
        assertVersionIs("(MC: 1.18 Pre-Release 8)", 18, 0, 8, -1);
        assertVersionIs("(MC: 1.17.1 pre-release 2)", 17, 1, 2, -1);
    }

    @Test
    void testMcVersion() {
        assertVersionIs("(MC: 1.18)", 18, 0, -1, -1);
        assertVersionIs("(MC: 1.17)", 17, 0, -1, -1);
        assertVersionIs("(MC: 1.16)", 16, 0, -1, -1);
    }

    @Test
    void testPatchVersion() {
        assertVersionIs("(MC: 1.18.1)", 18, 1, -1, -1);
        assertVersionIs("(MC: 1.17.2)", 17, 2, -1, -1);
        assertVersionIs("(MC: 1.16.3)", 16, 3, -1, -1);
    }

    private static void assertVersionIs(final String bukkitVersion, final int mc, final int patch, final int pre, final int rc) {
        assertEquals(
                createExpectedVersions(mc, patch, pre, rc),
                getVersionsList(createTestEnvironment(bukkitVersion))
        );
    }

    private static List<Integer> getVersionsList(final Environment env) {
        return createExpectedVersions(
                env.getMinecraftVersion(),
                env.getMinecraftPatchVersion(),
                env.getMinecraftPreReleaseVersion(),
                env.getMinecraftReleaseCandidateVersion()
        );
    }

    private static List<Integer> createExpectedVersions(final int mc, final int patch, final int pre, final int rc) {
        return List.of(mc, patch, pre, rc);
    }

    private static Environment createTestEnvironment(final String bukkitVer) {
        return new Environment(bukkitVer) {
            @Override
            public String getName() {
                return null;
            }
        };
    }
}
