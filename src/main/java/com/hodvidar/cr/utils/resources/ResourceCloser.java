package com.hodvidar.cr.utils.resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.HttpURLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Provides methods to ensure closing resources properly.
 */
public final class ResourceCloser {

    private static final Logger logger = LoggerFactory.getLogger(ResourceCloser.class.getSimpleName());

    private ResourceCloser() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ensures the closing of the given resource with no exception. Otherwise logs the failure.
     *
     * @param resource If <code>null</code> nothing happen
     */
    public static void closeResource(AutoCloseable resource) {
        if (resource == null) {
            return;
        }
        try {
            resource.close();
        } catch (Exception e) {
            logger.warn("Failed to close a resource", e);
        }
    }

    public static void disconnectConnection(HttpURLConnection resource) {
        if (resource == null) {
            return;
        }
        try {
            resource.disconnect();
        } catch (Exception e) {
            logger.warn("Failed to disconnect a connection", e);
        }
    }

    /**
     * Ensures the deletion of the given file with no exception. Otherwise logs the failure.
     * <p>
     * Does not handle the "isDirectory()" verification.
     *
     * @param aFile If <code>null</code> nothing happen
     */
    public static void deleteFile(File aFile) {
        if (aFile == null) {
            return;
        }

        String fileName = aFile.getName();
        try {
            if (aFile.delete()) {
                logger.info("Successfully deleted a file named '" + fileName + "'");
            } else {
                logger.warn("Failed to delete a file named '" + fileName + "'");
            }
        } catch (Exception e) {
            logger.warn("Failed to delete a file named '" + fileName + "'", e);
        }
    }

    /**
     * Ensures the deletion of file associated with the given path with no exception. Otherwise logs the failure.
     *
     * @param aPath If <code>null</code> nothing happen
     */
    public static void deleteFile(Path aPath) {
        if (aPath == null) {
            return;
        }

        String fileName = aPath.toString();
        try {
            Files.delete(aPath);
            logger.info("Successfully deleted a file named '" + fileName + "'");
        } catch (Exception e) {
            logger.warn("Failed to delete a file named '" + fileName + "'", e);
        }
    }

}
