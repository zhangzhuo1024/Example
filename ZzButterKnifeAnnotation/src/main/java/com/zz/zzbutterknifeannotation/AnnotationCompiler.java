package com.zz.zzbutterknifeannotation;

import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

/**
 * @author: zhuozhang6
 * @date: 2021/9/22
 * @email: zhuozhang6@iflytek.com
 * @Description:
 */
@AutoService(Processor.class)
public class AnnotationCompiler extends AbstractProcessor {

    private Filer filer;

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<>();
        set.add(ZzBindView.class.getCanonicalName());
        return set;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elementsAnnotatedWith = roundEnv.getElementsAnnotatedWith(ZzBindView.class);
        HashMap<String, List<VariableElement>> map = new HashMap<>();
        for (Element element : elementsAnnotatedWith) {
            VariableElement variableElement = (VariableElement) element;
            String activityName = variableElement.getEnclosingElement().getSimpleName().toString();
            List<VariableElement> list = map.get(activityName);
            if (list == null) {
                list = new ArrayList<>();
                map.put(activityName, list);
            }
            list.add(variableElement);
        }
        if (map.size() > 0) {
            Writer writer = null;
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String activityName = iterator.next();
                List<VariableElement> variableElements = map.get(activityName);
                //得到包名
                TypeElement enclosingElement = (TypeElement) variableElements.get(0).getEnclosingElement();
                String packageName = processingEnv.getElementUtils().getPackageOf(enclosingElement).toString();
                try {
                    JavaFileObject sourceFile = filer.createSourceFile(packageName + "." + activityName + "_ZzBindView");
                    writer = sourceFile.openWriter();
                    // package com.example.zzbutterknife;
                    writer.write("package " + packageName + ";\n");
                    // import com.zz.zzbutterknifeannotation.IBinder;
                    String name = IBinder.class.getPackage().getName();
                    writer.write("import " + name + ".IBinder;\n");
                    // public class AnnotationActivity_ZzBindView implements IBinder<
                    // com.example.zz.example.annotation.AnnotationActivity> {
                    writer.write("public class " + activityName + "_ZzBindView implements IBinder<" +
                            packageName + "." + activityName + ">{\n");
                    // public void bind(com.example.zz.example.annotation.AnnotationActivity target) {
                    writer.write(" @Override\n" +
                            " public void bind(" + packageName + "." + activityName + " target){");
                    //target.annotationText = (android.widget.TextView) target.findViewById(2131165223);
                    for (VariableElement variableElement : variableElements) {
                        //得到名字
                        String variableName = variableElement.getSimpleName().toString();
                        //得到id
                        int id = variableElement.getAnnotation(ZzBindView.class).value();
                        //得到类型
                        TypeMirror typeMirror = variableElement.asType();
                        writer.write("target." + variableName + "=(" + typeMirror + ")target.findViewById(" + id + ");\n");
                    }
                    writer.write("\n}}");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (writer != null) {
                        try {
                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        return false;
    }
}
