package org.raspberry.nexus.utils;

import org.raspberry.nexus.entity.PipelineStep;
import org.raspberry.nexus.exception.BadRequestException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtils {

    public static List<String> parseCommand(PipelineStep pipelineStep, Map<String, String> params) {
        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\S+)");
        Matcher matcher = pattern.matcher(pipelineStep.getCommand());

        while (matcher.find()) {
            String token = matcher.group(1);
            String value = parseToken(token, params);

            result.add(value);
        }

        return result;
    }

    private static String parseToken(String token, Map<String, String> params) {
        StringBuilder result = new StringBuilder();

        Pattern pattern = Pattern.compile("[{][{](.*?)[}][}]");
        Matcher matcher = pattern.matcher(token);

        while (matcher.find()) {
            String param = matcher.group(1);
            String value = parseParam(param, params);

            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }

        matcher.appendTail(result);

        return result.toString();
    }

    private static String parseParam(String param, Map<String, String> params) {
        if (!params.containsKey(param)) {
            throw new BadRequestException("Param with name '%s' not declared", param);
        }

        String value = params.get(param);

        return parseToken(value, params);
    }

}