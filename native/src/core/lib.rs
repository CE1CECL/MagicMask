pub use base;
use logging::*;

mod logging;

#[cxx::bridge]
pub mod ffi {
    extern "Rust" {
        fn rust_test_entry();
        fn android_logging();
        fn magicmask_logging();
        fn zygisk_logging();
    }
}

fn rust_test_entry() {}
