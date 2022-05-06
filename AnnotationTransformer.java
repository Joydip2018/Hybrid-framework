package tct.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import org.testng.annotations.ITestAnnotation;
import org.testng.IAnnotationTransformer;

public class AnnotationTransformer implements IAnnotationTransformer
{
    public void transform(final ITestAnnotation annotation, final Class testClass, final Constructor testConstructor, final Method testMethod) {
        annotation.setRetryAnalyzer((Class)RetryAnalyzer.class);
    }
}