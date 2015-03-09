# -*- mode: ruby -*-
# vi: set ft=ruby :

MOUNT_POINT = "/vagrant"

Vagrant::Config.run do |config|
  # Every Vagrant virtual environment requires a box to build off of.
  config.vm.box = "base-precise64"
  config.vm.host_name = "presence-tracker"

  # The url from where the 'config.vm.box' box will be fetched if it
  # doesn't already exist on the user's system.
  config.vm.box_url = "http://files.vagrantup.com/precise64.box"

  # Forward a port from the guest to the host, which allows for outside
  # computers to access the VM, whereas host only networking does not.
  # config.vm.forward_port 8000, 80 # python/django
  #config.vm.forward_port 8080, 8080 # python/django

  # Configure a private network required by nfs folder share
  config.vm.network :hostonly, "33.33.33.70"

  # NFS just does not work on windows
  if RUBY_PLATFORM =~ /mswin(32|64)/
    config.vm.share_folder("v-root", MOUNT_POINT, ".")
  else
    # Root shared folder using nfs for better performance
    config.vm.share_folder("v-root", MOUNT_POINT, ".", :nfs => true)
    config.vm.share_folder("v-m2", "/home/vagrant/.m2", "~/.m2", :nfs => true)
  end
end
