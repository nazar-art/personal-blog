package net.lelyak.edu.additional_tasks.post_processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author Nazar Lelyak.
 */
public class InjectRandomDataBeanAnnotationPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {
            InjectRandomData annotation = field.getAnnotation(InjectRandomData.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                RandomType randomType = annotation.type();

                switch (randomType) {
                    case NAME:
                        break;

                    case EMAIL:
                        break;

                    case STRING:
                        break;
                }

                field.setAccessible(true);
//                ReflectionUtils.setField(field, bean, );

            }
        }

        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
