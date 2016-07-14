package leonardo2204.com.materialnotes.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by leonardo on 7/14/16.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface DaggerScope {
    Class<?> value();
}
