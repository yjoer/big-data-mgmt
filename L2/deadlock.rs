use std::sync::{Arc, Mutex};
use std::thread;
use std::time::Duration;

fn main() {
    println!("Start!");

    let resource_1 = Arc::new(Mutex::new(0));
    let resource_2 = Arc::new(Mutex::new(0));

    let resource_1_ref = Arc::clone(&resource_1);
    let resource_2_ref = Arc::clone(&resource_2);

    let map = thread::spawn(move || {
        let _lock_1 = resource_1.lock().unwrap();

        thread::sleep(Duration::from_millis(100));
        let _lock_2 = resource_2.lock().unwrap();

        println!("Mapping!");
    });

    let reduce = thread::spawn(move || {
        let _lock_2 = resource_2_ref.lock().unwrap();

        thread::sleep(Duration::from_millis(100));
        let _lock_1 = resource_1_ref.lock().unwrap();

        println!("Reducing!");
    });

    map.join().unwrap();
    reduce.join().unwrap();
}
