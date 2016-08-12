'use strict'
const gulp = require('gulp');
const connect = require('gulp-connect');
const jshint = require('gulp-jshint');
const inject = require('gulp-inject');
const wiredep = require('wiredep').stream;
const preprocess = require('gulp-preprocess');

const concat = require('gulp-concat');
const rename = require('gulp-rename');
const uglify = require('gulp-uglify');
const minifycss = require('gulp-minify-css');
const sourcemaps = require('gulp-sourcemaps');
const clean = require('gulp-clean');

const rootPath = __dirname+'/';
const GULP_FILE = 'gulpfile.js';

const SOURCES_JS = [
    rootPath+'public/**/*.js',
    '!'+rootPath+'public/bower_components/**/*.js',
    '!'+rootPath+'public/_dist/*.js',
];

const SOURCES_JS_DIST = [
    rootPath+'public/_dist/**/*.min.js',
    '!'+rootPath+'public/bower_components/**/*.js',
];

const SOURCES_HTML = [
    rootPath+'public/**/*.html'
];

const SOURCES_CSS = [
    rootPath+'public/**/*.css',
    '!'+rootPath+'public/bower_components/**/*.css',
    '!'+rootPath+'public/_dist/*.css'
];

const SOURCES_CSS_DIST = [
    rootPath+'public/_dist/**/*.min.css',
    '!'+rootPath+'public/bower_components/**/*.css',
];

const SOURCES_SPEC = [
    rootPath+'tests/**/*.js'
];

const DIST_JS = rootPath+ 'public/_dist';
const DIST_CSS = rootPath+ 'public/_dist';
let IS_DIST = false;

const KARMA_CONF_FILE = rootPath + 'tests/karma.conf.js';
const SPEC_DIRECTORY = rootPath + 'tests/';
const SPEC_FILES = '"./**/*spec.js"';


gulp.task('serve', ['jshint', 'inject'], () => {
    const browserSync = require('browser-sync').create();
    browserSync.init({
        open: false,
        notify: false,
        port: process.env.PORT || 3000,
        server: {
            baseDir: './public'
        }
    });
    gulp.watch(SOURCES_CSS).on('change', () => {
        gulp.start('inject');
        browserSync.reload();
    });
    gulp.watch(SOURCES_JS).on('change', () => {
        gulp.start('jshint', 'inject');
        browserSync.reload();
    });
    gulp.watch(SOURCES_HTML).on('change', browserSync.reload);
});

gulp.task('serve-prod', ['build'], () => {
    connect.server({
        root: 'dist',
        port: process.env.PORT || 3000,
        livereload: false
    });
});

gulp.task('serveMin', ['isDist', 'serve']);


// jshint
gulp.task('jshint', () => {
    const JSHINT_JS = SOURCES_JS.concat(SOURCES_SPEC);

    return gulp.src(JSHINT_JS)
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// Inject
gulp.task('inject', () => {
    let options = {
        read: false,
        ignorePath: ['public'],
        addRootSlash: false
    };

    let wiredepOptions = {
        directory: rootPath+'public/bower_components',
        exclude: rootPath+'public/bower_components/angular-mocks/angular-mocks.js'
    };

    let source_js = [];
    let source_css = [];

    if (IS_DIST) {
        source_js = SOURCES_JS_DIST;
        source_css = SOURCES_CSS_DIST;
    } else {
        source_js = SOURCES_JS;
        source_css = SOURCES_CSS;
    }

    return gulp.src(rootPath+'public/index.html')
        .pipe(inject(gulp.src(source_js), options))
        .pipe(inject(gulp.src(source_css), options))
        .pipe(wiredep(wiredepOptions))
        .pipe(gulp.dest(rootPath+'public'));
});


// Karma
gulp.task('inject-karma', () => {
    // Inject all SOURCE_JS files
    function injectAppJsFiles(filepath, i, length) {
        return '"..' + filepath + '"' + (i + 1 < length ? ',\n            ' : '');
    }

    // Inject SPEC files
    function injectSpecFiles(i, length, extracted) {
        if (i + 1 == length) {
            extracted = extracted + ',\n            ' + SPEC_FILES;
        }
        return extracted;
    }

    gulp.src(KARMA_CONF_FILE)
        .pipe(inject(gulp.src(SOURCES_JS, {read: false}), {
            starttag: 'files: [',
            endtag: ']',
            transform: function (filepath, file, i, length) {
                const extracted = injectAppJsFiles(filepath, i, length);
                return injectSpecFiles(i, length, extracted);
            }
        })).pipe(gulp.dest(SPEC_DIRECTORY));
});

gulp.task('test', ['inject-karma'], function (done) {
    // Tests
    const argv = require('yargs').argv;
    const Server = require('karma').Server;
    let singleRun, browsers;
    if (argv.d) { // argument to debug
        singleRun = false;
        browsers = ['Chrome'];
    } else {
        singleRun = true;
        browsers = ['PhantomJS'];
    }

    new Server({
        browsers: browsers,
        configFile: KARMA_CONF_FILE,
        singleRun: singleRun
    }, function(karmaExitStatus) {
        if (karmaExitStatus) {
            process.exit(1);
        }
        done();
    }).start();
});

// Minify
gulp.task('minify', ['minify-js', 'minify-css']);

gulp.task('minify-js', () => {
    return gulp.src(SOURCES_JS)
        .pipe(concat('all.js'))
        .pipe(preprocess())
        .pipe(gulp.dest(DIST_JS))
        .pipe(rename('all.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest(DIST_JS));
});

gulp.task('minify-css', () => {
    return gulp.src(SOURCES_CSS)
        .pipe(concat('all.css'))
        .pipe(gulp.dest(DIST_CSS))
        .pipe(rename('all.min.css'))
        .pipe(sourcemaps.init())
        .pipe(minifycss())
        .pipe(sourcemaps.write())
        .pipe(gulp.dest(DIST_CSS));
});


gulp.task('build', ['isDist', 'jshint', 'minify', 'inject'], () => {
    const source = ['public/**/*.*'];
    return gulp.src(source)
        .pipe(gulp.dest('dist'));
});

gulp.task('isDist', () => {
    IS_DIST = true;
});
